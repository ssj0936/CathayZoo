package com.timothy.zoo.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timothy.zoo.MainApp.Companion.appContext
import com.timothy.zoo.R
import com.timothy.zoo.utils.isNetworkAvailable

class SplashScreenFragment:Fragment(){

    private val animationSet = AnimationUtils.loadAnimation(appContext, R.anim.splash_screen_animation)
            .apply {
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        val directions = SplashScreenFragmentDirections.actionSplashScreenFragmentToZooSectionFragment()
                        findNavController().navigate(directions)

                        disableFullScreen()
                    }
                })
            }

    private val mActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(!isNetworkAvailable(requireContext())){
            showNetworkConnectDialog()
        }else{
            val logo = view?.findViewById<AppCompatImageView>(R.id.zoo_logo)
            logo?.startAnimation(animationSet)
        }
    }

    private fun showNetworkConnectDialog(){
        AlertDialog.Builder(requireContext())
                .setMessage(R.string.dialog_network_require_message)
                .setPositiveButton(R.string.dialog_network_require_pos_btn
                ) { _, _ ->
                    val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    mActivityLauncher.launch(wifiIntent)
                }
                .setCancelable(false)
                .create().show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isNetworkAvailable(requireContext())){
            showNetworkConnectDialog()
        }else {
            val logo = view.findViewById<AppCompatImageView>(R.id.zoo_logo)
            logo.startAnimation(animationSet)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableFullScreen()
    }

    private fun enableFullScreen() {
        activity?.window?.apply {
            decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            statusBarColor = resources.getColor(R.color.background_color,null)
        }
    }

    fun disableFullScreen() {
        activity?.window?.apply {
            decorView.systemUiVisibility =
                    decorView.systemUiVisibility and
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() and
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()

            //get status bar color
            val typedValue = TypedValue()
            requireContext().theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
            val color = ContextCompat.getColor(requireContext(), typedValue.resourceId)

            statusBarColor = color
        }
    }
}