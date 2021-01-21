package cz.wayne.kmplayground.shared.data

@Serializable
data class JSONFile(
    val jsonrpc: Float = 2.0f,
    val result: Result
)