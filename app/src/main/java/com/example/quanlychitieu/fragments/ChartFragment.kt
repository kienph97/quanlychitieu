package com.example.quanlychitieu.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Fragment
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.FragmentChartBinding
import com.example.quanlychitieu.model.AppDatabase
import com.example.quanlychitieu.model.Item
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kal.rackmonthpicker.RackMonthPicker
import com.kal.rackmonthpicker.listener.DateMonthDialogListener
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Array

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChartFragment : androidx.fragment.app.Fragment(), DatePickerDialog.OnDateSetListener, DateMonthDialogListener, OnCancelMonthDialogListener {
    private lateinit var binding: FragmentChartBinding
    var month: Int = 0
    var year: Int = 0
    var list: ArrayList<Item>? = ArrayList()
    var listMonth: ArrayList<Item>? = ArrayList()
    var hashMap : HashMap<String, Int>? = HashMap()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater)
        val db = AppDatabase.getInstance(requireContext()).itemDao()
        list?.clear()
        GlobalScope.launch {
            list = ArrayList(db.getAll())
            activity?.runOnUiThread( Runnable {
                binding.llCalendar.setOnClickListener {
                    val calendar: Calendar = Calendar.getInstance()
                    month = calendar.get(Calendar.MONTH)
                    year = calendar.get(Calendar.YEAR)

                    val rackMonthPicker = RackMonthPicker(activity).setLocale(Locale.ENGLISH)
                            .setPositiveButton(DateMonthDialogListener { month, startDate, endDate, year, monthLabel ->
                                onDateMonth(month, startDate, endDate, year, monthLabel)})
                            .setNegativeButton(OnCancelMonthDialogListener { dialog: AlertDialog? ->
                                if (dialog != null) {
                                    onCancel(dialog)
                                }
                            })
                            .show()
                }
            }

            )

        }

        return binding.root
    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }


    @SuppressLint("NewApi")
    override fun onDateMonth(
        month: Int,
        startDate: Int,
        endDate: Int,
        year: Int,
        monthLabel: String?
    ) {
        Log.d("kien.ph", month.toString())
        binding.tvYear.setText(monthLabel)
        filterItemMonth(month, year)
        prepareDataToDraw()
        var listPieData : ArrayList<PieEntry> = ArrayList()
        hashMap?.forEach { key, value ->
            if (value != 0) {
                listPieData.add(PieEntry(value.toFloat(), key))
            }
        }
        var dataSet = PieDataSet(listPieData, "CHART")
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS.toMutableList())
        var pieData = PieData(dataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.YELLOW)
        binding.pieChart.data = pieData



    }

    override fun onCancel(dialog: AlertDialog) {
        dialog.dismiss()
    }

    private fun filterItemMonth(month: Int, year: Int) {
        if (!list.isNullOrEmpty()) {
            var m : String? = month.toString()
            if (month < 10) {
                m = "0"+month.toString()
            }

            listMonth?.clear()
            for (i in 0 until list!!.size) {
                if (list!![i].month.equals(m) && list!![i].year.equals(year.toString())){
                    listMonth!!.add(list!![i])
                }
            }
        }


    }


    @SuppressLint("NewApi")
    private fun prepareDataToDraw() {
        hashMap?.put("Thức ăn", 0)
        hashMap?.put("Đồ uống", 0)
        hashMap?.put("Shopping", 0)
        hashMap?.put("Quần áo", 0)
        hashMap?.put("Giay dép", 0)
        hashMap?.put("Tiền lương", 0)
        hashMap?.put("Tiền thưởng", 0)
        hashMap?.put("Chứng khoán", 0)
        hashMap?.put("Biếu, tặng", 0)
        hashMap?.put("Khác", 0)

        if (!listMonth.isNullOrEmpty()) {
            hashMap?.forEach { key, value ->
                for (i in 0 until listMonth!!.size) {
                    if (listMonth!![i].name.equals(key)) {
                        hashMap!!.put(key, value + listMonth!![i].money)
                    }
                }
            }

        }

    }
}

