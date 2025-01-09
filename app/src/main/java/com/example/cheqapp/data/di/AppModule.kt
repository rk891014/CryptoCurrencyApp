package com.example.cheqapp.data.di

import com.example.cheqapp.common.Constants
import com.example.cheqapp.data.remote.CoinPaprikaApi
import com.example.cheqapp.data.repository.CoinRepositoryImpl
import com.example.cheqapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providePaprikaApi(): CoinPaprikaApi {
//        we will tell dagger hilt, hey this is how you will create such a Coin Paprika Api
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

//    GsonConverterFactory -> please use json to serialize and deserialize the json data

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi) : CoinRepository {
        return CoinRepositoryImpl(api)
    }

}