package com.timothy.zoo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.timothy.zoo.R
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.databinding.FragmentPlantListLayoutBinding
import com.timothy.zoo.view.adapter.PlantListAdapter
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

        // only show title when collapsing
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange

            //at layout initialed totalScrollRange==0, it cause a title flash
            binding.collapsingToolbarLayout.isTitleEnabled = totalScrollRange!=0 && totalScrollRange + verticalOffset <= 0
        })

        mViewModel.mPlantResultsItem.observe(viewLifecycleOwner,
            Observer<List<PlantResultsItem?>> {
                if(!areSameList(it,adapter.getList())) {
                    //update recyclerview
                    adapter.swap(it.map { item -> item?.copy() })

                    binding.recyclerView.scheduleLayoutAnimation()
                }

                //disabling appbar scrolling, removing plant list title, divider and elevation when no plant data return
                if (it.isEmpty()) {
                    binding.collapsingToolbarLayout.apply {
                        this.layoutParams = (this.layoutParams as AppBarLayout.LayoutParams).apply { scrollFlags = 0 }
                        this.title = ""
                    }
                    binding.appbar.elevation = 0f
                    binding.layerNonEmptyElement.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
                //enable appbar scrolling
                else {
                    binding.collapsingToolbarLayout.apply {
                        this.layoutParams = (this.layoutParams as AppBarLayout.LayoutParams).apply {
                            scrollFlags = SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SCROLL
                        }
                    }
                }
        })

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.header.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.zoo_section_header_anim)
        )

    }

    private fun areSameList(list1:List<PlantResultsItem?>, list2: List<PlantResultsItem?>):Boolean{
        if(list1.size != list2.size) return false

        for(i in list1.indices){
            if(list1[i]?.fNameCh != list2[i]?.fNameCh ||
                    list1[i]?.fNameLatin != list2[i]?.fNameLatin ||
                    list1[i]?.fFeature != list2[i]?.fFeature)
                return false
        }
        return true
    }

    override fun itemClick(plantResultsItem: PlantResultsItem) {
        plantResultsItem.let {
            val direction = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(it)
            findNavController().navigate(direction)
        }
    }
}
