package com.carlyu.sidesenseapp.common.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.actions.ActionData
import com.carlyu.sidesenseapp.common.actions.ActionManager
import com.carlyu.sidesenseapp.common.actions.ActionType
import com.carlyu.sidesenseapp.common.util.BlurUtil
import com.carlyu.sidesenseapp.idd.types.EntryPointType
import com.carlyu.sidesenseapp.launcher.apps.suggestion.list.AutoAddShortcutDialogManager

class AutoAddShortcutDialogActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        AutoAddShortcutDialog().show(fragmentManager, AUTO_ADD_SHORTCUT_DIALOG)
    }

    class AutoAddShortcutDialog : DialogFragment() {

        private lateinit var mAutoAddShortcutDialogManager: AutoAddShortcutDialogManager

        private fun createEnableDialog(): Dialog {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.ss_strings_shortcut_offer_dialog_title_txt)
            builder.setView(mAutoAddShortcutDialogManager.getAutoAddDialogLayout())
            builder.setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                mAutoAddShortcutDialogManager.addSelectedShortcutsToCustomAppList()
                dialogInterface.dismiss()
                startSideSense()
            }
            return builder.create()
        }

        private fun startSideSense() {
            ActionManager.getInstance(activity).startAction(
                ActionType.SIDE_SENSE_MENU,
                ActionData.Builder()
                    .isAnimationFeedbackEnabled(false)
                    .entryPoint(EntryPointType.DIALOG)
                    .build()
            )
        }

        @Deprecated("Deprecated in Java")
        override fun onCancel(dialogInterface: DialogInterface) {
            super.onCancel(dialogInterface)
            activity?.finish()
        }

        @Deprecated("Deprecated in Java")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            mAutoAddShortcutDialogManager = AutoAddShortcutDialogManager.getInstance(activity)
            val dialog = createEnableDialog()
            BlurUtil.getInstance(activity).setBlur(dialog.window!!)
            return dialog
        }

        @Deprecated("Deprecated in Java")
        override fun onDismiss(dialogInterface: DialogInterface) {
            super.onDismiss(dialogInterface)
            activity?.let {
                if (!it.isFinishing) {
                    it.finish()
                }
            }
        }
    }

    companion object {
        private const val AUTO_ADD_SHORTCUT_DIALOG = "AutoAddShortcutDialog"
    }
}
