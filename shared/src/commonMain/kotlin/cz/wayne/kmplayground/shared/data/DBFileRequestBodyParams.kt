package cz.wayne.kmplayground.shared.data

@Serializable
data class DBFileRequestBodyParams(val os: String = "ANDROID", val version: String = "")
