package com.example.dictionaryapp.domain.repository

import android.util.Log
import com.example.dictionaryapp.data.local.WordInfoDao
import com.example.dictionaryapp.data.remote.DictionaryApi
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class WordInfoRepositoryImp(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos= dao.getWordInfo(word = word).map { it.toWordInfo() }
        emit(Resource.Loading(wordInfos))


        try{
            val remoteWordInfo = api.getWordInfo(word)
            dao.deleteWordInfo(remoteWordInfo.map { it.word })
            dao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })
        }catch (e: HttpException){
            emit(Resource.Error(
                message = "Something went wrong",
                data = wordInfos
            ))
        }catch (e: IOException){
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = wordInfos
            ))
        }

        val newWordInfo = dao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfo))
    }
}