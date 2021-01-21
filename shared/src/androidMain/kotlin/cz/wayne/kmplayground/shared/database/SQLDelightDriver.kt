package cz.wayne.kmplayground.shared.database



actual class SQLDelightDriver(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "test.db")
    }
}