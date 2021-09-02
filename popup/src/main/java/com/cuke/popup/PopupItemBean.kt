package com.cuke.popup

/**
 *  entity should extends this class
 */
abstract class PopupItemBean {
    var showName: String? = null
    var isSelect = false

    abstract fun getShowItemName(): String?
}