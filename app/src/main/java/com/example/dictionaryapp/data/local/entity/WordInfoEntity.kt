package com.example.dictionaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.WordInfo

@Entity(tableName = "wordInfo")
data class WordInfoEntity(
    @PrimaryKey val id: Int? = null,
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            license = license,
            meanings = meanings,
            phonetic = phonetic,
            word = word
        )
    }
}
