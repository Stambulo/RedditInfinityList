package com.stambulo.redditinfinitylist.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiHolder {

    @Named("baseUrl")
    @Provides
    fun baseUrl() = "https://www.reddit.com/r/aww/"

    @Singleton
    @Provides
    fun provideRetrofit(@Named("baseUrl") baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .client(creteOkHttpClient(MainInterceptor()))
            .build()

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Singleton
    @Provides
    fun provideMainService(retrofit: Retrofit): MainService = retrofit.create(MainService::class.java)

    @Singleton
    @Provides
    fun provideMainRemoteData(mainService: MainService): MainRemoteData = MainRemoteData(mainService)

    private fun creteOkHttpClient(interceptor: Interceptor): OkHttpClient{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class MainInterceptor: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }
}
