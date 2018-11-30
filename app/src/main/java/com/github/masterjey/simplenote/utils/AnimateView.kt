package com.github.masterjey.simplenote.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.github.masterjey.simplenote.R

class AnimateView(private val context: Context) {

    private var view: View? = null
    private var duration = 700

    fun view(view: View): AnimateView {
        this.view = view
        return this
    }

    fun duration(duration: Int): AnimateView {
        this.duration = duration
        return this
    }

    fun fadeIn() {
        fadeAnimation(R.anim.fade_in, View.VISIBLE)
    }

    fun fadeOut() {
        fadeAnimation(R.anim.fade_out, View.INVISIBLE)
    }

    private fun fadeAnimation(resId: Int, visibility: Int) {
        val animation = AnimationUtils.loadAnimation(context, resId)
        animation.duration = this.duration.toLong()

        if (view != null)
            view!!.startAnimation(animation)
        else
            IllegalArgumentException("view must be initialized use view() method")

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                view!!.visibility = visibility
            }
        })
    }

}