package com.honestyandpassion.ourcountry.Class

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.honestyandpassion.ourcountry.R

class DialogMsg(activity: Activity) {
    var dialog : Dialog? = null
    var cancel : Boolean? = null
    var dialogTitle: TextView? = null
    var dialogContent: TextView? = null
    var dialogAccept: Button? = null
    var dialogCancel: Button? = null
    var context: Activity? = null

    init{
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        context = activity
    }

    fun show() {
        if(dialog != null)
        {
            dialog!!.show()
        }
    }

    fun cancel() {
        if(dialog != null)
        {
            dialog!!.dismiss()
        }
    }

    fun showDialog(acceptText: String, cancelText: String, title: String, content: String) {
        dialog!!.setContentView(R.layout.dialog_standard)
        dialogTitle = dialog!!.findViewById(R.id.text_dialogtitle)
        dialogContent = dialog!!.findViewById(R.id.text_dialogcontent)
        dialogAccept = dialog!!.findViewById(R.id.btn_dialogaccept)
        dialogCancel = dialog!!.findViewById(R.id.btn_dialogcancel)

        dialogTitle!!.text = title
        dialogContent!!.text = content
        dialogAccept!!.setText(acceptText)
        dialogCancel!!.setText(cancelText)

        dialogAccept!!.setOnClickListener{
            context!!.moveTaskToBack(true)
            context!!.finishAndRemoveTask()
            android.os.Process.killProcess(android.os.Process.myPid())
        }

        dialogCancel!!.setOnClickListener{
            dialog!!.dismiss()
        }


        if (dialog!!.getWindow() != null) {
            dialog!!.getWindow()!!.attributes = getLayoutParams(dialog!!)
            dialog!!.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        }

    }

    private fun getLayoutParams(dialog: Dialog): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        if (dialog.window != null) {
            layoutParams.copyFrom(dialog.window!!.attributes)
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        return layoutParams
    }
}