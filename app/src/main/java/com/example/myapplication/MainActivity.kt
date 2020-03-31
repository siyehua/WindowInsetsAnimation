package com.example.myapplication

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setDecorFitsSystemWindows(false)
        setContentView(R.layout.activity_main)
        content.postDelayed({
            content.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        },5000)
        content.setOnApplyWindowInsetsListener { view, windowInsets ->
            val statusBars = windowInsets.getInsets(WindowInsets.Type.statusBars())
            // It's also possible to use multiple types
            val insets = windowInsets.getInsets(WindowInsets.Type.navigationBars())
            val insets2 = windowInsets.getInsets(WindowInsets.Type.ime())
            Log.e(
                TAG, "navigationBars:" + insets.top + " " + insets.bottom +
                        " " + windowInsets.isVisible(WindowInsets.Type.navigationBars())
            )
            Log.e(TAG, "statusBars:" + statusBars.top + " " + statusBars.bottom)
            Log.e(TAG, "insets2:" + insets2.top + " " + insets2.bottom)
            findViewById<View>(android.R.id.content).setPadding(0, 0, 0, insets.bottom)
            windowInsets
        }
        val callback = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            override fun onProgress(
                insets: WindowInsets,
                animations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {
                val navigationBars = insets.getInsets(WindowInsets.Type.navigationBars())
                val ime = insets.getInsets(WindowInsets.Type.ime())
                Log.e(
                    TAG, "ime:" + ime.top +
                            " " + ime.bottom
                )
                val parmas = (content.layoutParams as ViewGroup.MarginLayoutParams)
                parmas.bottomMargin = ime.bottom - navigationBars.bottom
                content.layoutParams = parmas
                return insets
            }

            override fun onStart(
                animation: WindowInsetsAnimation,
                bounds: WindowInsetsAnimation.Bounds
            ): WindowInsetsAnimation.Bounds {
                Log.e(
                    TAG,
                    "start lowerBound:" + bounds.lowerBound.top + " " + bounds.lowerBound.bottom
                )
                Log.e(
                    TAG,
                    "start upperBound:" + bounds.upperBound.top + " " + bounds.upperBound.bottom
                )
                Log.e(TAG, "start time:" + animation.durationMillis)
                return super.onStart(animation, bounds)
            }

            override fun onEnd(animation: WindowInsetsAnimation) {
                Log.e(TAG, "end:" + animation.durationMillis)
                super.onEnd(animation)
            }

            override fun onPrepare(animation: WindowInsetsAnimation) {
                Log.e(TAG, "onPrepare:" + animation.durationMillis)
                super.onPrepare(animation)
            }

        }
        content.setWindowInsetsAnimationCallback(callback)
    }
}
