package com.example.dictionaryapp.data.remote.dto

data class PhoneticDto(
    val audio: String,
    val license: LicenseDto,
    val sourceUrl: String,
    val text: String
)