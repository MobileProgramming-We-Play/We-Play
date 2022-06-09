package com.example.weplay.party

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import com.example.weplay.R
import com.example.weplay.SportsActivity
import com.example.weplay.databinding.ActivityPartyContentBinding
import com.example.weplay.domain.ParticipantInfo
import com.example.weplay.domain.Party
import com.example.weplay.party.notification.NotificationReceiver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.math.roundToInt

class PartyContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyContentBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var party: Party
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userName: String
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUser()
        initData()
        initMap()
        initChatting()
    }

    private fun initChatting() {
        party = intent.getSerializableExtra("party") as Party
        val participants = party.pparticipants

        if (participants.values.stream()
                .filter {
                    it?.uid == user.uid
                }
                .count()
                .toInt() != 0
        ) {
            binding.gotoChattingBtn.isEnabled = true
            binding.gotoChattingBtn.isClickable = true
        }

        binding.gotoChattingBtn.setOnClickListener {
            party = intent.getSerializableExtra("party") as Party
            val intent = Intent(this@PartyContentActivity, ChattingActivity::class.java)
            intent.putExtra("roomID", party.pid)
            startActivity(intent)
        }
    }

    private fun initUser() {
        auth = Firebase.auth
        user = auth.currentUser!!

        Firebase.database.getReference("Users/${user.uid}").get().addOnSuccessListener {
            userName = it.child("nickName").value.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        party = intent.getSerializableExtra("party") as Party

        with(binding) {
            val participants = party.pparticipants

            partyTitle.text = party.ptitle
            partyPlace.text = party.pplace
            partyContent.text = party.pcontent
            partyParticipantsNum.text = "${participants.size}/${party.pparticipantsNum}"

            calendar = Calendar.getInstance()
            calendar.timeInMillis = party.pdate

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DATE)
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            partyDate.text = "${year}. %02d.%02d, %02d:%02d".format(month, day, hour, minute)

            participants.values
                .stream()
                .forEach {
                    val textView = TextView(this@PartyContentActivity)
                    textView.text = it.uname

                    textView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
                    textView.layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
                    val uid = it.uid
                    textView.setOnClickListener {
                        val intent = Intent(this@PartyContentActivity, OthersProfileActivity::class.java)
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                    }

                    val marginLayoutParams = textView.layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.setMargins(
                        dpToPx(20, this@PartyContentActivity),
                        dpToPx(10, this@PartyContentActivity),
                        0, dpToPx(20, this@PartyContentActivity)
                    )

                    partyParticipants.addView(textView)
                }

            if (participants.size == party.pparticipantsNum ||
                    participants.values.stream()
                        .filter {
                            it?.uid == user.uid
                        }
                        .count()
                        .toInt() != 0
            ) {
                partyJoinBtn.isEnabled = false
                partyJoinBtn.isClickable = false
            } else {
                val index = intent.getStringExtra("firebaseIndex")
                partyJoinBtn.setOnClickListener {
                    Firebase.database.getReference("$index")
                        .child("pparticipants/${user.uid}")
                        .setValue(ParticipantInfo(user.uid, userName))

                    partyJoinBtn.isEnabled = false
                    partyJoinBtn.isClickable = false

                    // 알림 설정
                    startAlarm()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }


        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    private fun initMap() {
        val loc = LatLng(party.platitude, party.plongitude)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))

            val option = MarkerOptions()
            option.position(loc)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            option.title("위치")
            option.snippet("모임장소")
            googleMap.addMarker(option)?.showInfoWindow()

//            googleMap.uiSettings.setAllGesturesEnabled(false)

        }
    }

    private fun startAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 1,
            alarmIntent, 0
        )

        val alarmTime = calendar.timeInMillis - (24 * 60 * 60 * 1000)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //startActivity(Intent(this, PartiesActivity::class.java))
        finish()
    }
}