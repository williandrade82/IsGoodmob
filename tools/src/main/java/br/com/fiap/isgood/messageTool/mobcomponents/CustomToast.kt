package br.com.fiap.isgood.messageTool.mobcomponents

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.LayoutInflaterCompat
import br.com.fiap.isgood.messageTool.R

@SuppressLint("StaticFieldLeak")
object CustomToast {
    val KIND_ERROR = 4
    val KIND_INFORMATION = 3
    val KIND_WARNING = 2
    val KIND_SUCCESS = 1
    val KIND_DEFAULT = 0
    lateinit var activity: Activity

    fun showByKind(pActivity: Activity?, message: String, kind : Int){
        if (pActivity != null) {
            activity = pActivity
        }

        when (kind) {
            KIND_ERROR -> error(activity, message)
            KIND_INFORMATION -> info(activity, message)
            KIND_WARNING -> warning(activity, message)
            KIND_SUCCESS -> success(activity, message)
            KIND_DEFAULT -> default(activity, message)
            else -> default(activity, message)
        }
    }
    private fun showToast(
        pActivity: Activity?,
        backgroundToast: Drawable?,
        icon: Drawable?,
        message: String,
        duration: Int = Toast.LENGTH_SHORT,
    ) {
        if (pActivity != null) {
            activity = pActivity
        }
        if (activity == null){
            Log.e("CustomToast.showToast","Unknown activity for CustomToast")
        }
        val toastLayout : View = activity.layoutInflater.inflate(R.layout.custom_toast,null)
        val toast = Toast(activity)
        toast.view = toastLayout
        toast.view?.background = backgroundToast
        toastLayout.findViewById<TextView>(R.id.tvMessageToast).text = message
        val ivIconToast = toastLayout.findViewById<ImageView>(R.id.ivIconToast)
        if (icon == null){
            ivIconToast.visibility = View.GONE
        } else {
            ivIconToast.visibility = View.VISIBLE
            ivIconToast.setImageDrawable(icon)
        }
        toast.duration = duration
        toast.show()
    }
    fun success(pActivity: Activity?, message: String){
        if (pActivity != null) {
            CustomToast.activity = pActivity
        }
        showToast(
            activity,
            ContextCompat.getDrawable(activity, R.drawable.toast_bg_success),
            ContextCompat.getDrawable(activity, R.drawable.ic_success),
            message
        )
    }
    fun warning(pActivity: Activity?, message: String){
        if (pActivity != null) {
            activity = pActivity
        }
        showToast(
            activity,
            ContextCompat.getDrawable(activity, R.drawable.toast_bg_warning),
            ContextCompat.getDrawable(activity, R.drawable.ic_warning),
            message
        )
    }
    fun error(pActivity: Activity?, message: String){
        if (pActivity != null) {
            activity = pActivity
        }
        showToast(
            activity,
            ContextCompat.getDrawable(activity, R.drawable.toast_bg_error),
            ContextCompat.getDrawable(activity, R.drawable.ic_error),
            message
        )
    }
    fun info(pActivity: Activity?, message: String){
        if (pActivity != null) {
            activity = pActivity
        }
        showToast(
            activity,
            ContextCompat.getDrawable(activity, R.drawable.toast_bg_info),
            ContextCompat.getDrawable(activity, R.drawable.ic_info),
            message
        )
    }
    fun default(pActivity: Activity?, message: String){
        if (pActivity != null) {
            activity = pActivity
        }
        showToast(
            activity,
            ContextCompat.getDrawable(activity, R.drawable.toast_bg_default),
            null,
            message,
        )
    }
}