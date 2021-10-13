package com.cuke.habit.utils

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.graphics.Color
import android.text.InputType
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.Companion.DEFAULT_BEHAVIOR
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.afollestad.materialdialogs.utils.invalidateDividers
import com.cuke.habbit.R

object MaterialDialogUtils {

    fun showThemed(context: Context, title: String?, content: String?) {
        MaterialDialog(windowContext = context).show {
            title(text = title)
            message(text = content)
            positiveButton(text = "agree")
            negativeButton(text = "disagree")
            invalidateDividers(true,true)
            cancelOnTouchOutside(true)

        }
    }

    /***
     * 获取一个耗时等待对话框
     *
     * @param horizontal
     * @return MaterialDialog.Builder
     */
    fun showIndeterminateProgressDialog(
        context: Context,
        content: String?,
        horizontal: Boolean
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            message(text = content)
            cancelOnTouchOutside(false)
            setOnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) { //如果是按下，则响应，否则，一次按下会响应两次
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //activity.onBackPressed();
                    }
                }
                false //false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
            }
        }
    }


    /***
     * 获取基本对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showBasicDialog(
        context: Context,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            title(text=content)
            positiveButton(text="确定")
            negativeButton(text = "取消")
        }
    }

    /***
     * 显示一个基础的对话框  只有内容没有标题
     * @param
     * @return MaterialDialog.Builder
     */
    fun showBasicDialogNoTitle(
        context: Context,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            message(text = content)
            positiveButton(text="确定")
            negativeButton (text = "取消")
        }
    }


    /***
     * 显示一个基础的对话框  带标题 带内容
     * 没有取消按钮
     * @param
     * @return MaterialDialog.Builder
     */
    fun showBasicDialogNoCancel(
        context: Context,
        title: String?,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            message(text = content)
            title(text = title)
            positiveButton(text = "确定")
        }
    }

    /***
     * 显示一个基础的对话框  带标题 带内容
     * @param
     * @return MaterialDialog.Builder
     */
    fun showBasicDialog(
        context: Context,
        title: String?,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            message(text = content)
            title(text = title)
            positiveButton(text = "确定")
            negativeButton(text = "取消")
        }
    }

    /***
     * 显示一个基础的对话框  带标题 带内容
     * @return MaterialDialog.Builder
     */
    fun showBasicDialogPositive(
        context: Context,
        title: String?,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            message(text = content)
            title(text = title)
            positiveButton(text = "复制")
            negativeButton(text = "取消")
        }
    }

    /***
     * 选择图片等Item的对话框  带标题
     * @param
     * @return MaterialDialog.Builder
     */
    fun getSelectDialog(
        context: Context,
        title: String?,
        arrays: Array<String>
    ): MaterialDialog {
        val list= mutableListOf<String>()
        for (item in arrays){
            list.add(item)
        }
        val builder: MaterialDialog = MaterialDialog(windowContext = context)
            .listItems(items = list)
            .negativeButton(text="取消")
        if (!TextUtils.isEmpty(title)) {
            builder.title(text = title)
        }
        return builder
    }

    /***
     * 获取LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showBasicListDialog(
        context: Context,
        title: String?,
        content: List<CharSequence>?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            title(text = title)
            listItemsSingleChoice(items = content) { dialog, index, text -> }
            negativeButton(text = "取消") //                .checkBoxPromptRes(R.string.app_name, false, null)
        }
    }

    /***
     * 获取单选LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showSingleListDialog(
        context: Context,
        title: String?,
        content: List<CharSequence>?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            title(text = title)
            listItems(items = content) { dialog, index, text -> }
            listItemsSingleChoice(items = content)
            positiveButton(text = "选择")
        }
    }


    /***
     * 获取多选LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showMultiListDialog(
        context: Context,
        title: String?,
        content: List<CharSequence>?
    ): MaterialDialog {
        return MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(null, title)
            listItems(items = content)
            listItemsMultiChoice(disabledIndices = intArrayOf(0, 1)) { dialog, indices, items ->

            }
            positiveButton(text = "")
            noAutoDismiss()
            positiveButton()
            clearPositiveListeners()
        }
    }


    /***
     * 获取自定义对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showCustomDialog(context: Context, title: String?, content: Int) {
        val dialog: MaterialDialog = MaterialDialog(context).show {
            title(text = title)
            customView(content, scrollable = true)
            positiveButton(text = "确定") { }
            negativeButton(res = R.string.cancel)
        }
    }


    /***
     * 获取输入对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    fun showInputDialog(
        context: Context,
        title: String?,
        content: String?
    ): MaterialDialog {
        return MaterialDialog(windowContext = context).show {
            title(text = title)
            input(
                inputType = InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME or
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS
            ) { materialDialog, charSequence ->

            }
            positiveButton(text = "确定")
            negativeButton(text = "取消")
        }
    }
}