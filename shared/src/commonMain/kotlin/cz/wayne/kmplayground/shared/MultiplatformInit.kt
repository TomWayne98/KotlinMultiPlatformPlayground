package cz.wayne.kmplayground.shared

class MultiplatformInit() {

    fun init() {
    }


    fun createDatabase(driverFactory): Database {
        val driver = driverFactory.createDriver()
        val database = Database(driver)

        // Do more work with the database (see below).
    }
}