package cz.wayne.kmplayground.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.wayne.kmplayground.shared.Greeting
import android.widget.TextView
import cz.wayne.kmplayground.shared.SocketIOTest

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        SocketIOTest().connectToPrivateMessagesSocket {}
        SocketIOTest().connectToTestSocket({})



    }
}
