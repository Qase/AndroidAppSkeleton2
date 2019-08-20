package wtf.qase.appskeleton.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import quanti.com.kotlinlog.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("-- onCreate($savedInstanceState) --")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
