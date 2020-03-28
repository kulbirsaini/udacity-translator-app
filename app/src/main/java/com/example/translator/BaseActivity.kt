package com.example.translator

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(), AudioManager.OnAudioFocusChangeListener {

    // A shared MediaPlayer resource which will be used by all the activities. It's shared so that it can be released when switching activities.
    private var mPlayer: MediaPlayer? = null

    private val audioFocusLock = Any()
    private var resumeOnFocus = false
    private var playbackDelayed = false
    private var playbackAuthorized = false
    private var audioManager: AudioManager? = null
    private var afRequest: AudioFocusRequest? = null
    private val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()

    // Release MediaPlayer instance and abandon AudioFocus
    private fun releaseMediaPlayerAndAbandonAudioFocus() {
        Log.e("Audio", "release and abandon")
        mPlayer?.release()
        mPlayer = null

        // Abandon audio focus
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager?.abandonAudioFocusRequest(afRequest!!)
            audioManager = null
            afRequest = null
        } else {
            audioManager?.abandonAudioFocus(this)
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                synchronized(audioFocusLock) {
                    playbackDelayed = false
                    resumeOnFocus = false
                }

                Log.e("Audio", "Loss")
                mPlayer?.release()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                synchronized(audioFocusLock) {
                    playbackDelayed = false
                    resumeOnFocus = true
                }

                Log.e("Audio", "Loss Trans")
                mPlayer?.pause()
            }
            AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                Log.e("Audio", "Gain")
                if (playbackDelayed || resumeOnFocus) {
                    synchronized(audioFocusLock) {
                        playbackDelayed = false
                        resumeOnFocus = false
                    }

                    mPlayer?.start()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                Log.e("Audio", "Duck")
                synchronized(audioFocusLock) {
                    resumeOnFocus = true
                    playbackDelayed = false
                }

                mPlayer?.pause()
            }
        }
    }

    // Set the audio file to be played when the audio icon is clicked
    fun setAudio(soundId: Int) {
        // Releasing existing instance of media player
        this.releaseMediaPlayerAndAbandonAudioFocus()

        // Create new media player instance with provided sound resource
        mPlayer = MediaPlayer.create(this, soundId)

        audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(this)
                .build()

            synchronized(audioFocusLock) {
                playbackAuthorized = when (audioManager?.requestAudioFocus(afRequest!!)) {
                    AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> true
                    AudioManager.AUDIOFOCUS_REQUEST_DELAYED -> {
                        playbackDelayed = true
                        false
                    }
                    AudioManager.AUDIOFOCUS_REQUEST_FAILED -> false
                    else -> false
                }
            }
        } else {
            // Legacy audio focus request
            synchronized(audioFocusLock) {
                playbackAuthorized = when (audioManager?.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)) {
                    AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> true
                    AudioManager.AUDIOFOCUS_REQUEST_DELAYED -> {
                        playbackDelayed = true
                        false
                    }
                    AudioManager.AUDIOFOCUS_REQUEST_FAILED -> false
                    else -> false
                }
            }
        }

        // Start playing if authorized or inform via Toast
        when {
            playbackAuthorized -> {
                Log.e("Audio", "Authorized")
                mPlayer?.start()
            }
            playbackDelayed -> Toast.makeText(this, "Audio Focus delayed!", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, "Audio Focus not granted!", Toast.LENGTH_SHORT).show()
        }

        // Attach lister to release media player once the audio file is done playing
        mPlayer?.setOnCompletionListener {
            synchronized(audioFocusLock) {
                playbackDelayed = false
                resumeOnFocus = false
            }

            Log.e("Audio", "Completed")
            this.releaseMediaPlayerAndAbandonAudioFocus()
        }
    }

    // When user moved away from our app, we must release media player resource to prevent memory hogging
    override fun onPause() {
        super.onPause()
        Log.e("Audio", "onPause")
        mPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        Log.e("Audio", "onResume")
        mPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Audio", "onDestroy")
        this.releaseMediaPlayerAndAbandonAudioFocus()
    }
}