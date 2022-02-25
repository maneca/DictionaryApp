package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.WordInfo

data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            license = license.toLicense(),
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}