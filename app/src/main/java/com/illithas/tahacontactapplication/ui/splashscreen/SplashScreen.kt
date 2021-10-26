package com.illithas.tahacontactapplication.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.illithas.tahacontactapplication.R
import com.illithas.tahacontactapplication.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*




@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {


    private lateinit var fadeInAnimation: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()


        //Animations
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation)

        //Start Image Animation Fade in
        logo_image.animation = fadeInAnimation


        //Calling Intent to home page after 3 secs
        Handler(Looper.getMainLooper()).postDelayed({
            toHomePage()
        }, 3500)
    }


    private fun toHomePage() {
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }


}