package com.example.weplay.party

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.R
import com.example.weplay.databinding.ActivityMainBinding
import com.example.weplay.databinding.FragmentPartiesBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PartiesFragment : Fragment() {
    lateinit var binding: FragmentPartiesBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPartyAdapter
    lateinit var rdb: DatabaseReference
    var findQuery = false

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "참가했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPartiesBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun changeIntent(position: Int) {
        val intent = Intent(activity, PartyContentActivity::class.java)
        val party = adapter.getItem(position)
        intent.putExtra("party", party)
        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
        activityResultLauncher.launch(intent)
    }
    private fun init(){
        binding.apply {
            layoutManager = LinearLayoutManager(context)
            rdb = Firebase.database.getReference("Parties/party")
            val query: Query = rdb.limitToLast(50)
            val option = FirebaseRecyclerOptions.Builder<Party>()
                .setQuery(query, Party::class.java)
                .build()
            adapter = MyPartyAdapter(option)

            adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    /*val intent = Intent(activity, PartyContentActivity::class.java)
                    val party = adapter.getItem(position)
                    intent.putExtra("party", party)
                    intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                    activityResultLauncher.launch(intent)*/
                    changeIntent(position)
                }
            }
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            findbtn.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("psports").equalTo(pNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                       /* val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

            ///지역 버튼
            all.setOnClickListener {

                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            jongrogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("종로구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            junggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("중구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            yongsangu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("용산구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            seongdonggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("성동구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gwangjingu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("광진구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            dongdaemungu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("동대문구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            //////////
            jungranggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("중랑구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            seongbukgu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("성북구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gangbukgu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("강북구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            dobonggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("도봉구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            nowongu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("노원구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            eunpyeonggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("은평구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            seodaemungu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("서대문구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            /////////////
            mapogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("마포구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            yangcheongu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("양천구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gangseogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("강서구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gurogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("구로구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                       /* val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            geumcheongu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("금천구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                      /*  val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            yeongdeungpogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("영등포구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            /////////////
            dongjakgu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("동작구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gwanakgu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("관악구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                       /* val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            seochogu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("서초구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gangnamgu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("강남구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            songpagu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("송파구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
            gangdonggu.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("pcity").equalTo("강동구")
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        /*val intent = Intent(activity, PartyContentActivity::class.java)
                        val party = adapter.getItem(position)
                        intent.putExtra("party", party)
                        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                        startActivity(intent)*/
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
        }
        adapter.startListening()
    }

//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

}