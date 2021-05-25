package com.timothy.zoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.timothy.zoo.utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(!isNetworkAvailable(this)){
            showNetworkConnectDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isNetworkAvailable(this)){
            showNetworkConnectDialog()
        }
    }

    private fun showNetworkConnectDialog(){
        AlertDialog.Builder(this)
            .setMessage(R.string.dialog_network_require_message)
            .setPositiveButton(R.string.dialog_network_require_pos_btn
            ) { _, _ ->
                val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                mActivityLauncher.launch(wifiIntent)
            }
            .setCancelable(false)
            .create().show()
    }

}