package cz.wayne.kmplayground.shared

actual class Platform actual constructor() {

    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    actual val dbLocationPath: String = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
}

fun foo() {
}