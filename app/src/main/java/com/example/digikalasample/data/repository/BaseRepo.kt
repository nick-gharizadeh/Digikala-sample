package com.example.digikalasample.data.repository

import com.example.digikalasample.data.model.ErrorResponse
import com.example.digikalasample.data.model.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())
                    Resource.Error(
                        errorMessage = errorResponse?.message ?: handleServerRequestCode(
                            response.code()
                        )
                    )
                }

            } catch (e: IOException) {
                Resource.Error("به نظر میرسد دستگاه به اینترنت متصل نیست!")
            } catch (e: Exception) {
                Resource.Error(errorMessage = e.message.toString())
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}

fun handleServerRequestCode(code: Int): String {
    return when (code) {
        404 -> "نتیجه ای برای درخواست شما یافت نشد "
        410 -> "این منبع به طور دائمی حذف شده است"
        401 -> "دسترسی به این منبع غیر مجاز است"
        403 -> "دسترسی به این منبع ممنوع است"
        409 -> "درخواست تکراری است"
        in 300..399 -> "صفحه مورد نظر یافت نشد."
        in 400..499 -> "متاسفانه قادر به پردازش درخواست شما نیستیم! "
        in 500..599 -> "مشکلی از سمت سرور رخ داده است."
        else -> "مشکلی رخ داده است."
    }
}