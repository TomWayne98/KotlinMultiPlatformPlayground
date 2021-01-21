package cz.wayne.kmplaground.shared.database

expect class SQLDeligthDriver {
    expect fun createDriver(): SqlDriver
}