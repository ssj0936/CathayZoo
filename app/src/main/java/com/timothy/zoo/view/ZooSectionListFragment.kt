package com.timothy.zoo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.timothy.zoo.data.model.ZooSectionResultsItem
import com.timothy.zoo.databinding.FragmentZooSectionListLayoutBinding
import com.timothy.zoo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ZooSectionListFragment:Fragment() {

    private lateinit var mViewModel:MainViewModel
    private lateinit var binding:FragmentZooSectionListLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZooSectionListLayoutBinding.inflate(inflater,container,false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.mZooSectionResultsItem.observe(viewLifecycleOwner,
            Observer<List<ZooSectionResultsItem?>> {
                Timber.d("it:$it");
            })
    }
}