package com.ashish.ollama_chat_application.di

import com.ashish.ollama_chat_application.network.ChatApi
import com.ashish.ollama_chat_application.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class DiModule {

    @Provides
    @Singleton
    fun providesChatRepository(chatApi: ChatApi): ChatRepository = ChatRepository(chatApi)

    @Provides
    @Singleton
//    fun providesRetrofitInstance(): RetrofitInstance = RetrofitInstance()
    fun providesRetrofitInstance(): ChatApi {

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.179.17.248:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ChatApi::class.java)
    }
}