package com.canesvenatici.movies.data

import android.content.Context
import com.canesvenatici.movies.data.models.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

class JsonParser(private val context: Context) {

    fun parseStaffPicks(): List<MovieModel> {
        return try {
            val inputStream = context.assets.open("json/staff_picks.json")

            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }

            val gson = Gson()
            val listType = object : TypeToken<List<MovieModel>>() {}.type
            gson.fromJson(jsonString, listType)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun parseAllMovies(): List<MovieModel> {
        return try {
            val inputStream = context.assets.open("json/movies.json")

            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }

            val gson = Gson()
            val listType = object : TypeToken<List<MovieModel>>() {}.type
            gson.fromJson(jsonString, listType)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}