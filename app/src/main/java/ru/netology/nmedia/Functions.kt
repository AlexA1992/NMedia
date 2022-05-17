package ru.netology.nmedia

import android.text.Layout

class Functions {
    companion object Factory {
        var thisValue = 0
    }

    fun setString (value: Int) : String {
        var newValue: String = ""
        if(value in 0..999) {
            newValue = value.toString()
            return newValue
        }

        val millions = value/1000000
        if(millions >= 1) {
            val thisMills = "${millions.toString()}" + "M"
            newValue = thisMills
            return newValue
        }

        val afterMillions = value - millions
        val thousands = afterMillions/1000
        if(thousands >= 1) {
            val thisThous = "${thousands.toString()}" + "K"
            newValue = thisThous
            return newValue
        }
        return newValue
    }

    //поставить лайк
    fun add(value: Int): String {
        var newval = 0
        newval = value + 1
        val newString = Functions().setString(newval)
        return newString
    }

    //снять лайк
    fun reduce(value: Int): String {
        val newval = value - 1
        val newString = Functions().setString(newval)
        return newString
    }
}