package com.github.masterjey.simplenote.utils

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.github.masterjey.simplenote.R

class Animator(private val context: Context) {

    private var view: View? = null
    private var duration = 700

    fun view(view: View): Animator {
        this.view = view
        return this
    }

    fun duration(duration: Int): Animator {
        this.duration = duration
        return this
    }

    fun slidInUp(visibility: Int) {
        slide(R.anim.slide_in_up, visibility)
    }

    fun slidInUp() {
        slide(R.anim.slide_in_up, View.VISIBLE)
    }

    fun slidOutUp() {
        slide(R.anim.slide_out_up, View.INVISIBLE)
    }

    fun slidOutUp(visibility: Int) {
        slide(R.anim.slide_out_up, visibility)
    }

    fun fadeIn() {
        fadeAnimation(R.anim.fade_in, View.VISIBLE)
    }

    fun fadeOut() {
        fadeAnimation(R.anim.fade_out, View.INVISIBLE)
    }

    private fun slide(resId: Int, visibility: Int) {
        val animation = AnimationUtils.loadAnimation(context, resId)
        animation.duration = this.duration.toLong()

        startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                Handler().postDelayed({
                    view!!.visibility = visibility
                }, duration.toLong())

            }
        })
    }

    private fun fadeAnimation(resId: Int, visibility: Int) {
        val animation = AnimationUtils.loadAnimation(context, resId)
        animation.duration = this.duration.toLong()

        startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                view!!.visibility = visibility
            }
        })
    }

    private fun startAnimation(animation: Animation) {
        if (view != null)
            view!!.startAnimation(animation)
        else
            IllegalArgumentException("view must be initialized use view() method")
    }

}