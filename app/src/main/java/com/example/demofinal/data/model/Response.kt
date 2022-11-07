package com.example.demofinal.data.model

class Response<T>(
    var datas: MutableList<T>? = null,
    var state: Int = 0,
    var message: String? = null
) {


    companion object {
        fun <T> success(list: MutableList<T>): Response<T> {
            return Response(
                datas = list,
                message = null,
                state = State.SUCCESS
            )
        }

        fun <T> notFound(): Response<T> {
            return Response(
                datas = null,
                message = "NOT FOUND",
                state = State.ERROR
            )
        }

        fun <T> loading(caches: MutableList<T>? = null): Response<T> {
            return Response(
                datas = caches,
                message = "",
                state = State.LOADING
            )
        }
    }


    object State {
        const val LOADING = 0
        const val SUCCESS = 1
        const val ERROR = 2
    }
}