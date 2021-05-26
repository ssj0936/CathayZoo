package com.timothy.zoo.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.timothy.zoo.R
import com.timothy.zoo.databinding.FragmentPlantDetailLayoutBinding
import com.timothy.zoo.viewmodel.PlantDetailViewModel

class PlantDetailFragment:DialogFragment() {
    private val mViewModel:PlantDetailViewModel by viewModels()
    lateinit var binding:FragmentPlantDetailLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.attributes?.windowAnimations = R.style.DetailDialogAnimation
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetailLayoutBinding.inflate(inflater,container,false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    //size of dialog
    override fun onStart() {
        super.onStart()

        dialog?.let{
            it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.window?.setBackgroundDrawableResource(R.drawable.background_plant_detail_dialog)
        }
    }
}