package com.example.dictionaryapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionaryapp.data.util.JsonParser
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromJsonToMeanings(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun fromMeaningsToJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromJsonToLicense(json: String): License {
        return jsonParser.fromJson<License>(
            json,
            object : TypeToken<License>(){}.type
        ) ?: License("", "")
    }

    @TypeConverter
    fun fromLicenseToJson(license: License): String {
        return jsonParser.toJson(
            license,
            object : TypeToken<License>(){}.type
        ) ?: ""
    }
}