package com.timothy.zoo.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.timothy.zoo.R
import com.timothy.zoo.databinding.FragmentZooSectionListLayoutBinding
import com.timothy.zoo.utils.isNetworkAvailable
import com.timothy.zoo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class ZooSectionListFragment:Fragment() {
    private val RESULT_CODE_WIFI = 1

    private val mViewModel by viewModels<MainViewModel>()
    private lateinit var binding:FragmentZooSectionListLayoutBinding


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

        if(!isNetworkAvailable(requireContext())){
            showNetworkConnectDialog()
        }else {
            _queryZooSectionData()
        }
    }

    private fun showNetworkConnectDialog(){
        AlertDialog.Builder(requireContext())
                .setMessage(R.string.dialog_network_require_message)
                .setPositiveButton(R.string.dialog_network_require_pos_btn
                ) { _, _ ->
                    val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivityForResult(wifiIntent,RESULT_CODE_WIFI)
                }
                .setCancelable(false)
                .create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            RESULT_CODE_WIFI-> {
                if (isNetworkAvailable(requireContext())) {
                    _queryZooSectionData()
                }else{
                    showNetworkConnectDialog()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun _queryZooSectionData(){
        mViewModel.queryZooSectionData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("$it")
            },{error-> Timber.e(error)})
    }
}