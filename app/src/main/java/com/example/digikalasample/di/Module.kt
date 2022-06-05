package com.example.digikalasample.di

import com.example.digikalasample.network.DigiKalaApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Singleton
    @Provides
    fun getMoshi(): Moshi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return moshi
    }

    @Singleton
    @Provides
    fun getRetrofit(moshi: Moshi): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .build()
        return retrofit
    }


    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): DigiKalaApiService {
        return retrofit.create(DigiKalaApiService::class.java)
    }


}