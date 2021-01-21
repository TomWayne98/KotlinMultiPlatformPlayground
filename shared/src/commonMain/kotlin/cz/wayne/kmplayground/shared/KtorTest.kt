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

@Serializable
data class JSONFile(
    val jsonrpc: Float = 2.0f,
    val result: Result
)

@Serializable
data class Result(
    val sports: List<Sport>
    )

@Serializable
data class Sport(
    @SerialName("id_sport")
    val idSport: Int,
    @SerialName("sport_name")
    val sportName: String,
    @SerialName("sport_code")
    val sportCode: String,
    @SerialName("sport_order")
    val sportOrder: Int,
    val regions: List<Region>
)

@Serializable
data class Region(
    @SerialName("id_oppty_region")
    val idOpptyRegion: Int,
    @SerialName("oppty_region_name")
    val opptyRegionName: String,
    @SerialName("oppty_region_code")
    val opttyRegionCode: String? = null,
    @SerialName("region_order")
    val regionOrder: Int,
    val leagues: List<League>
)

@Serializable
data class League(
    @SerialName("id_league")
    val leagueId: Int,
    @SerialName("league_name")
    val leagueName: String,
    @SerialName("event_note")
    val eventNote: String? = null,
    // TODO: count of sidebets is ignored
    //@SerialName("count_of_sidebets")
    val matches: List<Match>
)

@Serializable
data class Match(
    @SerialName("oppty_info_number")
    val optyInfoNumber: Int,
    @SerialName("oppty_end_date_format")
    val optyEndDateFormat: String? = null,
    @SerialName("oppty_end_time_format")
    val optyEndTimeFormat: String,
    @SerialName("team_name_1")
    val teamName1: String? = null,
    @SerialName("team_name_2")
    val teamName2: String? = null,
    @SerialName("oppty_name")
    val optyName:  String? = null,
    @SerialName("id_opportunity")
    val opportunityId: Int,
    @SerialName("oppty_end_date")
    val opptyEndDate:String? = null,
    @SerialName("oppty_end_time")
    val opptyEndTime:String? = null,
    @SerialName("oppty_note")
    val opptyNote:String? = null,
    @SerialName("oppty_type_name")
    val opptyTypeName:  String? = null,
    @SerialName("id_oppty_tree_type")
    val opptyTreeTypeId: Int,
    val odds: List<Odd>
)

@Serializable
data class Odd(
    @SerialName("tip_name")
    val tipName: String? = null,
    @SerialName("tip_shortname")
    val tipShortname: String? = null,
    @SerialName("odds_value")
    val oddsValue: Float,
    @SerialName("odds_value_numeric")
    val oddsValueNumber: Float,
    @SerialName("id_odds")
    val oddsId: Int,
    @SerialName("id_tip")
    val tipId: Int,
    @SerialName("in_ticket")
    val inTicket: Boolean
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
data class JSONRequestBody(
    @SerialName("app-id")
    val appId: String = "1eea0ad7-6075-40ff-bff4-a9915697bb03",
    val id: Int = 1,
    val jsonrpc: Float = 2.0f,
    val lang: String = "pl",
    val method: String = "prematch.offer",
    val params: DBFileRequestBodyParams = DBFileRequestBodyParams(),
    val session: String = "1eea0ad7-6075-40ff-bff4-a9915697bb03",
    @SerialName("station-name")
    val stationName: String = "androidapp"
)

@Serializable
data class DBFileRequestBodyParams(val os: String = "ANDROID", val version: String = "")
