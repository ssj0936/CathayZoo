package com.timothy.zoo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.timothy.zoo.data.model.ZooSectionResultsItem
import com.timothy.zoo.databinding.FragmentZooSectionListLayoutBinding
import com.timothy.zoo.view.adapter.ZooSectionListAdapter
import com.timothy.zoo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ZooSectionListFragment:Fragment(),ZooSectionListAdapter.OnClickListener {
    private lateinit var mViewModel:MainViewModel
    private lateinit var binding:FragmentZooSectionListLayoutBinding
    private val adapter = ZooSectionListAdapter(this)

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

        binding.recyclerView.adapter = adapter
        mViewModel.mZooSectionResultsItem.observe(viewLifecycleOwner,
            Observer<List<ZooSectionResultsItem?>> {
                if(!areSameList(it, adapter.getList())) {
                    adapter.swap(it.map { item ->
                        item?.copy()
                    })
                    binding.recyclerView.scheduleLayoutAnimation()
                }
            })
    }

    override fun itemClick(zooSectionResultsItem: ZooSectionResultsItem) {
        try {
            zooSectionResultsItem.let {
                val direction = ZooSectionListFragmentDirections
                        .actionZooSectionFragmentToPlantListFragment(it)
                findNavController().navigate(direction)
            }
        }catch (e:IllegalArgumentException){
            Timber.d("two tap preventing")
        }

    }

    private fun areSameList(list1:List<ZooSectionResultsItem?>, list2: List<ZooSectionResultsItem?>):Boolean{
        if(list1.size != list2.size) return false

        for(i in list1.indices){
            if(list1[i]?.id != list2[i]?.id) return false
        }
        return true
    }
}