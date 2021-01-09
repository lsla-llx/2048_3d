package com.example.a2048_3d

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStart(intent: Intent, startID: Int) {
        super.onStart(intent, startID)

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgm)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
    }


}
