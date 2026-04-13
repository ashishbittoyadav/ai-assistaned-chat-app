package com.ashish.ollama_chat_application.di

import com.ashish.ollama_chat_application.network.RetrofitInstance
import com.ashish.ollama_chat_application.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DiModule {

    @Provides
    @Singleton
    fun providesChatRepository(retrofitInstance: RetrofitInstance): ChatRepository = ChatRepository(retrofitInstance)

    @Provides
    @Singleton
    fun providesRetrofitInstance(): RetrofitInstance = RetrofitInstance()
}