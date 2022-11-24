package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()


        var lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)


        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            run {
                startActivity(Intent(this, MainActivity::class.java))
                lottieAnimationView.pauseAnimation()
                finish()
            }
        }, 3500)

    }
}