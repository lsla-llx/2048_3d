package com.example.a2048_3d

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.app.Activity
import android.media.SoundPool
import android.view.View
import android.os.Bundle
import com.example.a2048_3d.BeginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        begin.setOnClickListener(){

            val intentMusic = Intent(this,MusicService::class.java)
            startService(intentMusic)

            val intent = Intent(this,BeginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }
        setting.setOnClickListener(){
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }
        market.setOnClickListener(){
            val intent = Intent(this,MarketActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }
        exit.setOnClickListener(){
            finish()
        }
    }
}