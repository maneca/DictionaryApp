package com.example.dictionaryapp.domain.model

data class WordInfo(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)
