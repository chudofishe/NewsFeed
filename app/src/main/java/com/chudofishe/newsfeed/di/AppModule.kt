package com.chudofishe.newsfeed.di

import com.chudofishe.newsfeed.BuildConfig
import com.chudofishe.newsfeed.data.remote.AuthInterceptor
import com.chudofishe.newsfeed.data.remote.NewArticlesRepositoryImpl
import com.chudofishe.newsfeed.data.remote.NewsApiService
import com.chudofishe.newsfeed.data.remote.NewsPagingSource
import com.chudofishe.newsfeed.domain.repository.NewArticlesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.NEWS_API_KEY))
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.NEWS_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsArticlesRepository(pagingSource: NewsPagingSource.Factory): NewArticlesRepository {
        return NewArticlesRepositoryImpl(pagingSource)
    }
}