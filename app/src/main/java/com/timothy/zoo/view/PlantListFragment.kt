package com.timothy.zoo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.databinding.FragmentPlantListLayoutBinding
import com.timothy.zoo.view.adapter.PlantListAdapter
import com.timothy.zoo.view.widgit.VerticalRecyclerviewDecoration
import com.timothy.zoo.viewmodel.PlantListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlantListFragment:Fragment(), PlantListAdapter.OnClickListener {
    private val mViewModel:PlantListViewModel by viewModels()
    private lateinit var binding:FragmentPlantListLayoutBinding
    private var adapter = PlantListAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantListLayoutBinding.inflate(inflater,container,false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            binding.collapsingToolbarLayout.isTitleEnabled = totalScrollRange + verticalOffset <= 0
        })
        mViewModel.mPlantResultsItem.observe(viewLifecycleOwner,
            Observer<List<PlantResultsItem?>> {
                adapter.swap(it.map { item ->
                    item?.copy()
                })
            })
    }

    override fun itemClick(plantResultsItem: PlantResultsItem) {
        Timber.d("click")
    }
}