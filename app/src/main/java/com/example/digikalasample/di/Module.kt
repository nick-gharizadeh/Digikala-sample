package com.example.digikalasample.di

import android.content.Context
import androidx.room.Room
import com.example.digikalasample.data.db.AddressesDataBase
import com.example.digikalasample.data.db.AddressesDataBase.Companion.DATABASE_NAME
import com.example.digikalasample.network.DigiKalaApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object Module {


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AddressesDataBase {
        return Room.databaseBuilder(
            context,
            AddressesDataBase::class.java, DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .build()
    }



    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): DigiKalaApiService {
        return retrofit.create(DigiKalaApiService::class.java)
    }


}