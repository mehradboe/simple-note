package com.github.masterjey.simplenote.utils

import android.content.Context
import com.github.masterjey.simplenote.R
import java.text.SimpleDateFormat
import java.util.*

fun convertToDate(context: Context, timeStamp: Long): String {
    val local = Locale(
            context.getString(R.string.language)
            , context.getString(R.string.country)
    )
    val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", local)
    val date = Date(timeStamp)
    return simpleDateFormat.format(date)
}