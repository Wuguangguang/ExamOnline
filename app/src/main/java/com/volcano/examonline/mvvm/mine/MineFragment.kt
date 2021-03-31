package com.volcano.examonline.mvvm.mine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.databinding.MineFragmentBinding
import com.volcano.examonline.mvvm.mine.adapter.FooterAdapter
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel

class MineFragment : Fragment() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val viewModel: MineViewModel by lazy{ ViewModelProvider(this).get(MineViewModel::class.java) }
    private val footerAdapter : FooterAdapter by lazy { FooterAdapter(activity!!,viewModel.footers) }
    private lateinit var binding : MineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MineFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding.mineFooterRcv.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = footerAdapter
        }

    }

    private fun initData() {

    }

}