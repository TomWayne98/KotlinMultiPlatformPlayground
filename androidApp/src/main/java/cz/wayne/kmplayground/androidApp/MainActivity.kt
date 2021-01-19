package cz.wayne.kmplayground.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cz.wayne.kmplayground.shared.Greeting
import android.widget.TextView
import cz.wayne.kmplayground.shared.TestApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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


        MainScope().launch {
            val dbFile = TestApi().getDB()
            Log.d("TOMW", "DbFile data: ${dbFile.result?.data}")
        }
    }

}
