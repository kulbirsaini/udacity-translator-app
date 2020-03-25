package com.example.translator

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    // A shared MediaPlayer resource which will be used by all the activities. It's shared so that it can be released when switching activities.
    private var mPlayer: MediaPlayer? = null

    // Set the audio file to be played when the audio icon is clicked
    fun setAudio(soundId: Int) {
        // Releasing existing instance of media player
        mPlayer?.release()

        // Create new media player instance with provided sound resource
        mPlayer = MediaPlayer.create(this, soundId)
        mPlayer?.start()

        // Attach lister to release media player once the audio file is done playing
        mPlayer?.setOnCompletionListener {
            mPlayer?.release()
        }
    }

    // When user moved away from our app, we must release media player resource to prevent memory hogging
    override fun onPause() {
        super.onPause()
        mPlayer?.release()
    }
}