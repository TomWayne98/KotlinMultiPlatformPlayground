package cz.wayne.kmplayground.shared

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.serializer
import java.io.*
import cz.wayne.kmplayground.shared.data.*

class TestApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun downloadDB(): DBFile {
        return httpClient.post<DBFile>(DB_ENDPOINT) {
            // When you send body in form of data class (serialized to JSON) you need to add this header
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = DBFileRequestBody()
        }
    }


    suspend fun getDB(): DBFile {
        val data = downloadDB().result!!.data
        val sourceStream: ByteArrayInputStream = ByteArrayInputStream(
            android.util.Base64.decode(data, android.util.Base64.DEFAULT)
        )
        val mDbFileTemp = File(Platform().dbLocationPath, "temp_prem.db");


        val inputStream: java.util.zip.GZIPInputStream = java.util.zip.GZIPInputStream(sourceStream)
        val outputStream = FileOutputStream(mDbFileTemp);

        writeInputStreamToDBFile(inputStream, outputStream)

        // InputSource(GZIPInputStream(downloadDB().result!!.data))
        return downloadDB()
    }

    suspend fun updatePrematchJSON() {
        val result = downloadJSON()
        android.util.Log.d("TOMW", ": Downloaded")
        val sports = result.result.sports
        android.util.Log.d("TOMW", ": Sport size ${sports.size}")
        val regions = mutableListOf<Region>()
        val leagues = mutableListOf<League>()
        val matches = mutableListOf<Match>()
        val odds = mutableListOf<Odd>()

        sports.forEach { it ->
            android.util.Log.d("TOMW", ": Region: ${it.regions}")
            regions.addAll(it.regions)
        }

        regions.forEach {
            leagues.addAll(it.leagues)
        }

        leagues.forEach {
            matches.addAll(it.matches)
        }

        matches.forEach {
            odds.addAll(it.odds)
        }

        android.util.Log.d("TOMW", "We parsed like: ${odds.size} odds")

    }

    suspend fun downloadJSON() : JSONFile {
        return httpClient.post<JSONFile>(DB_ENDPOINT) {
            // When you send body in form of data class (serialized to JSON) you need to add this header
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = JSONRequestBody()
        }
    }

    private fun writeInputStreamToDBFile(inputStream: java.io.InputStream,outputStream: FileOutputStream) {
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            outputStream.write(buffer, 0, len)
        }
    }

    companion object {
        private const val DB_ENDPOINT = "https://mapi.sts.pl/"
    }
}
