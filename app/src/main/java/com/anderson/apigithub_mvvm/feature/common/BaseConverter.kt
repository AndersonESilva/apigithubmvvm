package com.anderson.apigithub_mvvm.feature.common

/**
 * Created by anderson on 16/11/2020.
 */
interface BaseConverter<INPUT, RETURN> {

    fun convert(input: INPUT): RETURN

}