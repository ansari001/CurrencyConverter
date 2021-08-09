package com.test.itfaq.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.test.itfaq.listeners.OnDialogGenericListener
import com.test.itfaq.R

class DialogUtil {
    companion object {
        fun showInfoDialog(
            mContext: Context,
            title: String,
            mListener: OnDialogGenericListener
        ) {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_info)
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val tvTitle = dialog.findViewById<AppCompatTextView>(R.id.tvTitle)
            val btnOkay = dialog.findViewById<AppCompatButton>(R.id.btnOkay)

            tvTitle.text = title

            btnOkay.setOnClickListener {
                mListener.onPositiveClick()
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}