package com.example.quanlychitieu.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.FragmentSettingBinding

class SettingFragment : androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }
}