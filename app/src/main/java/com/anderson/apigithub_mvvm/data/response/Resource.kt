package com.anderson.apigithub_mvvm.data.response

/**
 * Created by anderson on 15/11/2020.
 */
class Resource<T>(val status: Status, val data: T?, val error: String?){

    enum class Status {
        INIT,
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> init(data: T? = null): Resource<T> {
            return Resource(Status.INIT, data, null)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}