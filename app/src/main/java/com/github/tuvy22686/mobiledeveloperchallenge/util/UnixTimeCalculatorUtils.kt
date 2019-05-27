package com.github.tuvy22686.mobiledeveloperchallenge.util

object UnixTimeCalculatorUtils {

    fun isMoreThan30Minutes(now: Long, recordedTime: Long): Boolean {
        return (now - recordedTime) > 1800
    }
}