package com.sanalkasif.movieapp.utils.extensions
import com.sanalkasif.movieapp.model.entities.enums.ResponseType
import com.sanalkasif.movieapp.utils.FinishListener
import com.sanalkasif.movieapp.utils.ResponseManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.makeApiCall(FinishListener: FinishListener<ResponseManager<T>>?) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.let {
                FinishListener?.onFinish(response.handleResponse())
            } ?: run {
                FinishListener?.onFinish(ResponseManager.error("Api Error"))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            FinishListener?.onFinish(ResponseManager(ResponseType.ERROR, null, t.localizedMessage))
        }
    })
}
fun <T> Response<T>.handleResponse(): ResponseManager<T> {

    if (this.isSuccessful) {
        val data = this.body()
        return ResponseManager.success(data, "Successful")
    } else {

        return when {
            //We can handle all error codes here
            this.code() == 408 -> {
                ResponseManager.error("Timeout")
            }
            this.code() == 402 -> {
                ResponseManager.error("Payment Required")
            }
            this.code() == 400 -> {
                ResponseManager.error("Bad Request")
            }
            else -> {
                ResponseManager.error("Unexpected error")
            }
        }
    }
}



