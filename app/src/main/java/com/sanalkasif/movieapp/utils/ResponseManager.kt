package com.sanalkasif.movieapp.utils

import com.sanalkasif.movieapp.model.entities.enums.ResponseType

class ResponseManager<T>(var status: ResponseType, val data: T?, val message: String?){

    companion object {
        fun <T> success(data: T?,message:String?): ResponseManager<T> {
            return ResponseManager(ResponseType.SUCCESS, data, message)
        }

        fun <T> error(msg: String): ResponseManager<T> {
            return ResponseManager(ResponseType.ERROR, null, msg)
        }

        fun <T> loading(): ResponseManager<T> {
            return ResponseManager(ResponseType.LOADING, null, null)
        }
    }
}


