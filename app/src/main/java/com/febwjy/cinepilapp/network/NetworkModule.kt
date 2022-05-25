package com.febwjy.cinepilapp.network

import android.app.Application
import android.content.Context
import com.febwjy.cinepilapp.data.repository.MovieRepositoryImpl
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.utils.Constant
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

/**
 * Created by Febby Wijaya on 19/05/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideNewsAPI() : MovieService {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieService) : MovieRepository {
        return MovieRepositoryImpl(api)
    }

}