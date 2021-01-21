package cz.wayne.kmplayground.shared.data

@Serializable
data class DBFileError(
    val code: String,
    val message: String
)