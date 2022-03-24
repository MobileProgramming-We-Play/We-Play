package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    val countries = mutableListOf<String>(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val multiAutoCompleteTextView =
            findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val msg = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        val items = resources.getStringArray(R.array.countries_array)
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )

        multiAutoCompleteTextView.setAdapter(adapter)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        editText.addTextChangedListener {
            val str = it.toString()
            button.isEnabled = str.isNotEmpty()
        }

//        editText.addTextChangedListener(
//            afterTextChanged = {
//                val str = it.toString()
//                button.isEnabled = str.isNotEmpty()
//            }
//        )

//        editText.addTextChangedListener(object:TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                val str = p0.toString()
//                button.isEnabled = str.isNotEmpty()
//                //한글자라도 입력하면 버튼 활성화
//            }
//        })

            button.setOnClickListener {
                adapter.add(editText.text.toString())
                adapter.notifyDataSetChanged()  //data 변경됐을 때 갱신
                editText.text.clear()
            }
        }
    }




