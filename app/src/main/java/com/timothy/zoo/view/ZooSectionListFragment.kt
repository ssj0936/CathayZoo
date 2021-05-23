package com.timothy.zoo.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.timothy.zoo.R
import com.timothy.zoo.api.ZooSectionService
import com.timothy.zoo.databinding.FragmentZooSectionListLayoutBinding
import com.timothy.zoo.utils.isNetworkAvailable
import com.timothy.zoo.viewmodel.MainViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class ZooSectionListFragment:Fragment() {

    private lateinit var viewModel:MainViewModel
    private lateinit var binding:FragmentZooSectionListLayoutBinding

    val RESULT_CODE_WIFI = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZooSectionListLayoutBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isNetworkAvailable(requireContext())){
            showNetworkConnectDialog()
        }else {
            queryZooSectionData()
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
                    queryZooSectionData()
                }else{
                    showNetworkConnectDialog()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun queryZooSectionData(){
        val baseURL = "https://data.taipei/"

        val service = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ZooSectionService::class.java)

        service.searchAllZooSection()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it == null) {
                        Timber.d("respond = null")
                    } else {
                        Timber.d("$it")
                    }
                }
    }
}