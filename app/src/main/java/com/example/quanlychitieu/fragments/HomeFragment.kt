package com.example.quanlychitieu.fragments

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.FragmentHomeBinding
import com.example.quanlychitieu.model.AppDatabase
import com.example.quanlychitieu.model.Item
import com.example.quanlychitieu.model.adapter.AdapterToday
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<AdapterToday.ViewHoder>? = null
    private var list : ArrayList<Item>? = null
    private var listToday : ArrayList<Item>? = null
    private var mSpending = 0
    private var mCollect = 0
    private var mSum = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        val db = AppDatabase.getInstance(requireContext()).itemDao()
        list?.clear()
        GlobalScope.launch {
            list = ArrayList(db.getAll())

            if (list != null) {
                filterItemToday(list!!)
                calculateBalance(list!!)
                activity?.runOnUiThread(Runnable {
                    if (listToday != null) {
                        layoutManager = LinearLayoutManager(context)
                        binding.rvTotalSpending.layoutManager = layoutManager
                        adapter = AdapterToday(listToday!!)
                        binding.rvTotalSpending.adapter = adapter
                    }
                    binding.tvCollect.text = "Thu nhập: " + mCollect.toString() + " VND"
                    binding.tvSpend.text = "Chi tiêu: " + mSpending.toString() + " VND"
                    binding.tvTotalSpending.text = "Tổng: " + mSum.toString() + " VND"
                })
            }


        }

        return binding.root
    }

    @SuppressLint("NewApi")
    fun filterItemToday(list : ArrayList<Item>) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)
        Log.d("kien.ph", formatted)
        listToday = ArrayList()
        listToday?.clear()
        for (i in 0 until list.size) {
            var str : String = list[i].year + "" +list[i].month + "" + list[i].day
            if (str.equals(formatted)) {
                listToday?.add(list[i])
            }
        }
        Log.d("kien.ph", listToday.toString())
    }

    fun calculateBalance(list : ArrayList<Item>) {
        mCollect = 0
        mSpending = 0
        mSum = 0
        for (i in 0 until list.size) {
            if (list[i].type.equals("Thu nhập")) {
                mCollect += list[i].money
            } else {
                mSpending += list[i].money
            }
        }
        mSum = mCollect - mSpending
    }
}