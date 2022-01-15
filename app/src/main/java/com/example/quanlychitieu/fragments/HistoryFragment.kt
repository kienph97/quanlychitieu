package com.example.quanlychitieu.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.FragmentHistoryBinding
import com.example.quanlychitieu.model.AppDatabase
import com.example.quanlychitieu.model.Item
import com.example.quanlychitieu.model.adapter.AdapterHistory
import com.example.quanlychitieu.model.adapter.AdapterToday
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private var mLayoutManager : RecyclerView.LayoutManager? = null
    private var mAdapter : RecyclerView.Adapter<AdapterHistory.ViewHoder>? = null
    private var mListItemHistory : ArrayList<Item>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        val db = AppDatabase.getInstance(requireContext()).itemDao()
        mListItemHistory?.clear()


        GlobalScope.launch {
            mListItemHistory = ArrayList(db.getAll())
//            db.deleteAll()
            activity?.runOnUiThread(Runnable {
                if (!mListItemHistory.isNullOrEmpty()) {
                    mLayoutManager = LinearLayoutManager(context)
                    binding.rvHistory.layoutManager = mLayoutManager
                    mAdapter = AdapterHistory(mListItemHistory!!)
                    binding.rvHistory.adapter = mAdapter

                }
            })

        }


        return binding.root
    }
}