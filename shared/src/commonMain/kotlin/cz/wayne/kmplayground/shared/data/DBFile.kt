package cz.wayne.kmplayground.shared.data


@Serializable
data class DBFile(
    val id: Int?,
    val session: String? = null,
    val ip: String? = null,
    val version: String? = null,
    val result: DBFileResult? = null,
    val error: DBFileError? = null
)