package com.ashish.ollama_chat_application.interceptor

import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.math.pow

class RetryInterceptor(private val maxRetries: Int = 3) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var attempt = 0
        var response : Response? = null
        var exception : IOException? = null

        if(chain.request().method != "GET"){
            return chain.proceed(request)
        }

        while (attempt<this.maxRetries){
            try {
                response = chain.proceed(request)
                if (response.isSuccessful){
                    return response
                }
            }catch (e: IOException){
                exception = e
            }
            attempt++

            // Exponential backoff
            try {
                if (response?.code !in 500..599){
                    break
                }
                val delay = (2.0.pow(attempt.toDouble()) * 1000).toLong()
                Thread.sleep(delay)
            } catch (_: InterruptedException) {}
        }


        response?.let { return it }
        throw exception ?: IOException("unknown network error...!!!")
    }
}