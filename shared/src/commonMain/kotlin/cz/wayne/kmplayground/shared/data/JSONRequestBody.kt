package cz.wayne.kmplayground.shared.data

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
