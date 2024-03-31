package com.example.editfiledemo

import android.app.Application
import android.content.ContentResolver
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val listAudio = MutableLiveData<List<AudioModel>>()
    private val contentResolver: ContentResolver = application.contentResolver
    lateinit var itemChoose: AudioModel

    init {
        getAllAudioFiles()
    }

    private fun getAllAudioFiles() {
        val audioList = mutableListOf<AudioModel>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DATE_ADDED,
            MediaStore.Audio.AudioColumns.SIZE
        )

        contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val pathIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val dateAddedIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.SIZE)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(pathIndex)
                val dateAddedMillis = cursor.getLong(dateAddedIndex)
                val sizeBytes = cursor.getLong(sizeIndex)

                val dateAddedFormatted = formatDateAdded(dateAddedMillis)
                val sizeFormatted = formatSize(sizeBytes)

                val audioFile = AudioModel(name, path, dateAddedFormatted, sizeFormatted)
                audioList.add(audioFile)
            }
        }
        listAudio.value = audioList
    }

    private fun formatDateAdded(dateAddedMillis: Long): String {
        val instant = Instant.ofEpochMilli(dateAddedMillis * 1000)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
        return localDateTime.format(formatter)
    }

    private fun formatSize(sizeBytes: Long): String {
        val kilobytes = sizeBytes / 1024.0
        return String.format("%.2f kB", kilobytes)
    }
}