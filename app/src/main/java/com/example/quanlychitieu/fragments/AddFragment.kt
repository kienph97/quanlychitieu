 package com.example.quanlychitieu.fragments

import android.app.Application
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.FragmentAddBinding
import com.example.quanlychitieu.model.AppDatabase
import com.example.quanlychitieu.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

 class AddFragment : androidx.fragment.app.Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentAddBinding
     var day = 0
     var month: Int = 0
     var year: Int = 0
     var myDay = 0
     var myMonth: Int = 0
     var myYear: Int = 0

     var mDay: String = "01"
     var mMonth : String = "01"
     var mYear : String = "2022"
     var mType : String = ""
     var mMoney : Int  = 0
     var mUnit : String = ""
     var mNameSpending = ""
     var mDetail : String = ""

     var list : List<Item>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        //spending or collect
        val spending = resources.getStringArray(R.array.Spending)
        if (binding.spinnerSpendOrCollect != null) {
            val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, spending) }
            binding.spinnerSpendOrCollect.adapter = adapter

            binding.spinnerSpendOrCollect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mType = adapter?.getItem(position).toString()
                    Toast.makeText(activity, mType, Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    mType = "Thu nhập"
                    Toast.makeText(activity, mType, Toast.LENGTH_SHORT).show()
                }
            }
        }
        //unit
        val unit = resources.getStringArray(R.array.Unit)
        if (binding.spinnerUnit != null) {
            val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, unit) }
            binding.spinnerUnit.adapter = adapter

            binding.spinnerUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mUnit = adapter?.getItem(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    mUnit = "VND"
                }
            }
        }
        //Type
        val type = resources.getStringArray(R.array.Type)
        if (binding.spinnerType != null) {
            val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, type) }
            binding.spinnerType.adapter = adapter

            binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mNameSpending = adapter?.getItem(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    mNameSpending = "Thức ăn"
                }
            }
        }

        //date

        binding.tvDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                    activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day) }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

        //detail
        mDetail = binding.edtDetail.text.toString()
        //Money
        if (binding.edtMoney.text.toString() != null && binding.edtMoney.text.toString() != "") {
            mMoney = binding.edtMoney.text.toString().toInt()
        }

        val db = AppDatabase.getInstance(requireContext()).itemDao()
        binding.imbtSave.setOnClickListener {
            Toast.makeText(activity, "Đã thêm", Toast.LENGTH_SHORT).show()
            GlobalScope.launch {

                mDetail = binding.edtDetail.text.toString()
                mMoney = binding.edtMoney.text.toString().toInt()
                var item  = Item(mDay, mMonth, mYear, mType, mNameSpending, mMoney, mUnit, mDetail)
                db.insertItem(item)
                list = ArrayList(db.getAll())
            }
        }


        return binding.root
    }

     override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
         myDay = dayOfMonth
         myYear = year
         myMonth = month +1


         mDay = dayOfMonth.toString()
         if (dayOfMonth < 10) {
             mDay = "0" + mDay
         }
         mMonth = (month+1).toString()
         if (month+1 < 10) {
             mMonth = "0"+ mMonth
         }
         mYear = year.toString()
         var str : String = mDay + "/" + mMonth + "/" + mYear
         binding.tvDate.setText(str)
         val calendar: Calendar = Calendar.getInstance()

//         val timePickerDialog = TimePickerDialog(this@MainActivity, this@MainActivity, hour, minute,
//                 DateFormat.is24HourFormat(this))
//         timePickerDialog.show()
     }
 }