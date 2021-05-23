package com.timothy.zoo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.timothy.zoo.utils.isNetworkAvailable
import com.timothy.zoo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mViewModel:MainViewModel
    private val RESULT_CODE_WIFI = 1

    private val mActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (isNetworkAvailable(this)) {
            queryZooSection()
        }else{
            showNetworkConnectDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

//        queryZooSection()
        if(!isNetworkAvailable(this)){
            showNetworkConnectDialog()
        }else {
            queryZooSection()
        }
    }

    private fun queryZooSection(){
        mViewModel.queryZooSectionData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Activity:$it")
                mViewModel.test.value = "DONE"
                it?.let {
                    mViewModel.mZooSectionResultsItem.value = it
                }
            },{error-> Timber.e(error)})
            .apply {
                mCompositeDisposable.add(this)
            }
    }

    private fun showNetworkConnectDialog(){
        AlertDialog.Builder(this)
            .setMessage(R.string.dialog_network_require_message)
            .setPositiveButton(R.string.dialog_network_require_pos_btn
            ) { _, _ ->
                val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                mActivityLauncher.launch(wifiIntent)
//                startActivityForResult(wifiIntent,RESULT_CODE_WIFI)
            }
            .setCancelable(false)
            .create().show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when(requestCode){
//            RESULT_CODE_WIFI-> {
//                if (isNetworkAvailable(this)) {
//                    queryZooSection()
//                }else{
//                    showNetworkConnectDialog()
//                }
//            }
//            else -> super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.dispose()
    }
}