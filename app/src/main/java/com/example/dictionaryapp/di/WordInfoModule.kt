package com.example.dictionaryapp.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.data.local.Converters
import com.example.dictionaryapp.data.local.WordInfoDatabase
import com.example.dictionaryapp.data.remote.DictionaryApi
import com.example.dictionaryapp.data.util.GsonParser
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.domain.repository.WordInfoRepositoryImp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: DictionaryApi,
        database: WordInfoDatabase
    ): WordInfoRepository{
        return WordInfoRepositoryImp(api, database.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase{
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_info_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}