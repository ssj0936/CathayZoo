package com.timothy.zoo.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timothy.zoo.R

class SplashScreenFragment:Fragment(){

    private val animationDuration:Long = 1500

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationSet = AnimationSet(true).apply {
            fillAfter = true
            interpolator = AnticipateInterpolator()

            val alphaAnimation = AlphaAnimation(1f,0f).apply {
                duration = animationDuration
            }

            val scaleAnimation = ScaleAnimation(
                    1f,1.2f,
                    1f,1.2f,
                    Animation.RELATIVE_TO_SELF,.5f,
                    Animation.RELATIVE_TO_SELF,.5f
            ).apply {
                duration = animationDuration
            }
            addAnimation(alphaAnimation)
            addAnimation(scaleAnimation)

            setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    val directions = SplashScreenFragmentDirections.actionSplashScreenFragmentToZooSectionFragment()
                    findNavController().navigate(directions)

                    disableFullScreen()
                }
            })
        }

        val logo = view.findViewById<AppCompatImageView>(R.id.zoo_logo)

        logo.startAnimation(animationSet)
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