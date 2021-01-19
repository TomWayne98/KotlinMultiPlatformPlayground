package cz.wayne.kmplayground.shared

expect class Platform() {
    val platform: String

    val dbLocationPath: String
}