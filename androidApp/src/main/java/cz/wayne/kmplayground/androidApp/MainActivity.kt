package cz.wayne.kmplayground.androidApp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import cz.wayne.kmplayground.shared.Greeting
import cz.wayne.kmplayground.shared.TestApi
import kotlinx.coroutines.GlobalScope


fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
/*
        SocketIOTest().connectToPrivateMessagesSocket {}
        SocketIOTest().connectToTestSocket({})*/
        if (!hasPermission()) {
            askForPermissions()
        } else {
           MainScope().launch {
               val timeStart = System.currentTimeMillis()
               Log.d("TOMW", "Hej 1")
               val dbFile = TestApi().updatePrematchJSON()
               val timeEnd = System.currentTimeMillis()
               Log.d("TOMW", "Hej 2 - it took ${timeEnd - timeStart} ms")
            }

        }
    }

    private fun hasPermission(): Boolean {
            // Check if we have write permission
            val permission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions() {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
            this,
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (permissions.size > 0 && grantResults.size > 0) {

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    }

}
