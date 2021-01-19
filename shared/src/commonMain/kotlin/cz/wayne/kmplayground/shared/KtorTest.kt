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

@Serializable
data class DBFile(
    val id: Int?,
    val session: String? = null,
    val ip: String? = null,
    val version: String? = null,
    val result: DBFileResult? = null,
    val error: DBFileError? = null
)

@Serializable
data class DBFileError(
    val code: String,
    val message: String
)

@Serializable
data class DBFileResult(
    val data: String
)

class TestApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getDB(): DBFile {
        return httpClient.post<DBFile>(DB_ENDPOINT) {
            // When you send body in form of data class (serialized to JSON) you need to add this header
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = DBFileRequestBody()
        }
    }

    companion object {
        private const val DB_ENDPOINT = "https://mapi.sts.pl/"
    }
}

@Serializable
data class DBFileRequestBody(
    @SerialName("app-id")
    val appId: String = "1eea0ad7-6075-40ff-bff4-a9915697bb03",
    val id: Int = 1,
    val jsonrpc: Float = 2.0f,
    val lang: String = "pl",
    val method: String = "prematch.dbdata",
    val params: DBFileRequestBodyParams = DBFileRequestBodyParams(),
    val session: String = "1eea0ad7-6075-40ff-bff4-a9915697bb03",
    @SerialName("station-name")
    val stationName: String = "androidapp"
)

@Serializable
data class DBFileRequestBodyParams(val os: String = "ANDROID", val version: String = "")
