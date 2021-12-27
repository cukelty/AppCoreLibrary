package com.cuke.base.http.entity

data class ApiResponse<T>(val success:Boolean, val data :T, val msg:String,val code:Int)