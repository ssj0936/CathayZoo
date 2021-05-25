package com.timothy.zoo.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.timothy.zoo.MainApp.Companion.appContext
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
    private val actionBarSize:Int
    private val headerFakeHeight:Int = appContext.resources.getDimensionPixelSize(R.dimen.headerFakeViewHeight)

    init {
        val typedValue = TypedValue()
        appContext.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
        val attribute = intArrayOf(android.R.attr.actionBarSize)
        val array = appContext.obtainStyledAttributes(typedValue.resourceId, attribute)
        actionBarSize = array.getDimensionPixelSize(0, -1) + headerFakeHeight
        array.recycle()

    }

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

        mViewModel.mPlantResultsItem.observe(viewLifecycleOwner,
            Observer<List<PlantResultsItem?>> {
                if(!areSameList(it,adapter.getList())) {
                    //update recyclerview
                    adapter.swap(it.map { item -> item?.copy() })

                    binding.recyclerView.scheduleLayoutAnimation()
                }
            })

        headerSetup()

        //listener of btn
        binding.navButtonMain.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun headerSetup(){
        //animation on first launch
        binding.header.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.zoo_section_header_anim)
        )

        //listener of knowing when header should show
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            val title = v.findViewById<TextView>(R.id.plant_list_title)

            //scroll down(finger down)
            if(oldScrollY>scrollY && (v.scrollY.toFloat() < title.y)){
                if(mViewModel.isTopTitleShow.value != false)
                    mViewModel.isTopTitleShow.value = false
            }
            // scroll up(finger up)
            else if(oldScrollY<scrollY && (v.scrollY.toFloat() >= title.y)){
                if(mViewModel.isTopTitleShow.value != true)
                    mViewModel.isTopTitleShow.value = true
            }
        }

        //observing header status and playing animation
        mViewModel.isTopTitleShow.observe(viewLifecycleOwner,Observer<Boolean>{
            when(it){
                true ->{
                    val anim = ObjectAnimator.ofFloat(
                            binding.topStickyTitleContainer,
                            "translationY",
                            actionBarSize.toFloat()*-1,
                            headerFakeHeight.toFloat()*-1
                    ).apply {
                        duration = 200
                        interpolator = AnticipateOvershootInterpolator()
                    }.start()
                }
                false->{
                    val anim = ObjectAnimator.ofFloat(
                            binding.topStickyTitleContainer,
                            "translationY",
                            headerFakeHeight.toFloat()*-1,
                            actionBarSize.toFloat()*-1
                    ).apply {
                        duration = 200
                    }.start()
                }
            }
        })

        //listener of btn
        binding.navButton.setOnClickListener {
            findNavController().navigateUp()
        }
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
