package com.trunghoang.generalapp.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Hoang Trung on 18/07/2019
 */

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)