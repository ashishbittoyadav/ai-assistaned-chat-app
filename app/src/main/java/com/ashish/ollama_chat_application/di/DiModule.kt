package com.ashish.ollama_chat_application.di

import com.ashish.ollama_chat_application.api_annotation.AIChatApiAnnotation
import com.ashish.ollama_chat_application.api_annotation.DragonBallApiAnnotation
import com.ashish.ollama_chat_application.network.ChatApi
import com.ashish.ollama_chat_application.network.DragonBallZApi
import com.ashish.ollama_chat_application.repository.ChatRepository
import com.ashish.ollama_chat_application.repository.DragonBallZRepository
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
    fun providesDragonBallRepository(dragonBallZApi: DragonBallZApi): DragonBallZRepository = DragonBallZRepository(dragonBallZApi)

    @AIChatApiAnnotation
    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
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
    }

    @Provides
    fun providesChatApi(@AIChatApiAnnotation retrofit: Retrofit): ChatApi{
        return retrofit.create(ChatApi::class.java)
    }

    @Provides
    fun providesDragonBallApi(@DragonBallApiAnnotation retrofit: Retrofit): DragonBallZApi{
        return retrofit.create(DragonBallZApi::class.java)
    }

    @DragonBallApiAnnotation
    @Provides
    @Singleton
    fun providesDragonBallApiRetrofit(): Retrofit {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}