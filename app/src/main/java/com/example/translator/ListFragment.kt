package com.example.translator

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class ListFragment: Fragment(), AudioManager.OnAudioFocusChangeListener {
    companion object {
        const val FRAGMENT_NUMBERS = 1
        const val FRAGMENT_FAMILY_MEMBERS = 2
        const val FRAGMENT_COLORS = 3
        const val FRAGMENT_PHRASES = 4
    }

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
        Log.e("TransLog", "release and abandon")
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

                Log.e("TransLog", "Loss")
                mPlayer?.release()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                synchronized(audioFocusLock) {
                    playbackDelayed = false
                    resumeOnFocus = true
                }

                Log.e("TransLog", "Loss Trans")
                mPlayer?.pause()
            }
            AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                Log.e("TransLog", "Gain")
                if (playbackDelayed || resumeOnFocus) {
                    synchronized(audioFocusLock) {
                        playbackDelayed = false
                        resumeOnFocus = false
                    }

                    mPlayer?.start()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                Log.e("TransLog", "Duck")
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
        mPlayer = MediaPlayer.create(context, soundId)

        audioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager

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
                Log.e("TransLog", "Authorized")
                mPlayer?.start()
            }
            playbackDelayed -> Toast.makeText(context, "Audio Focus delayed!", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, "Audio Focus not granted!", Toast.LENGTH_SHORT).show()
        }

        // Attach lister to release media player once the audio file is done playing
        mPlayer?.setOnCompletionListener {
            synchronized(audioFocusLock) {
                playbackDelayed = false
                resumeOnFocus = false
            }

            Log.e("TransLog", "Completed")
            this.releaseMediaPlayerAndAbandonAudioFocus()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("TransLog", "NumbersFragment:onCreateView")

        return inflater.inflate(R.layout.activity_list_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("TransLog", "NumbersFragment:onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        val fragmentId = arguments!!.getInt(FragmentCallback.FRAGMENT_ID)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TranslatedItemsAdapter(getFragmentItems(fragmentId), getBackgroundColor(fragmentId), this@ListFragment)
        }
    }

    // When user moved away from our app, we must release media player resource to prevent memory hogging
    override fun onPause() {
        super.onPause()
        Log.e("TransLog", "onPause")
        this.releaseMediaPlayerAndAbandonAudioFocus()
    }

    private fun getBackgroundColor(fragmentId: Int): Int {
        return when (fragmentId) {
            FRAGMENT_COLORS -> R.color.category_colors
            FRAGMENT_FAMILY_MEMBERS -> R.color.category_family_member
            FRAGMENT_PHRASES -> R.color.category_phrases
            else -> R.color.category_numbers
        }
    }

    private fun getFragmentItems(fragmentId: Int): ArrayList<TranslatedItem> {
        var englishWords = ArrayList<String>()
        var originalWords = ArrayList<String>()
        var images = ArrayList<Int>()
        var sounds = ArrayList<Int>()

        when (fragmentId) {
            FRAGMENT_NUMBERS -> {
                englishWords = arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
                originalWords = arrayListOf("एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस")
                images = arrayListOf(R.drawable.number_one, R.drawable.number_two, R.drawable.number_three, R.drawable.number_four, R.drawable.number_five,
                    R.drawable.number_six, R.drawable.number_seven, R.drawable.number_eight, R.drawable.number_nine, R.drawable.number_ten)
                sounds = arrayListOf(R.raw.number_one, R.raw.number_two, R.raw.number_three, R.raw.number_four, R.raw.number_five,
                    R.raw.number_six, R.raw.number_seven, R.raw.number_eight, R.raw.number_nine, R.raw.number_ten)
            }
            FRAGMENT_COLORS -> {
                englishWords = arrayListOf("red", "green", "brown", "gray", "black", "white", "dusty yellow", "mustard yellow")
                originalWords = arrayListOf("लाल", "हरा", "भूरा", "स्लेटी", "काला", "सफेद", "धूल भरा पीला", "सरसों का पीला")
                images = arrayListOf(R.drawable.color_red, R.drawable.color_green, R.drawable.color_brown, R.drawable.color_gray,
                    R.drawable.color_black, R.drawable.color_white, R.drawable.color_dusty_yellow, R.drawable.color_mustard_yellow)
                sounds = arrayListOf(R.raw.color_red, R.raw.color_green, R.raw.color_brown, R.raw.color_gray,
                    R.raw.color_black, R.raw.color_white, R.raw.color_dusty_yellow, R.raw.color_mustard_yellow)
            }
            FRAGMENT_FAMILY_MEMBERS -> {
                englishWords = arrayListOf("father", "mother", "son", "daughter", "older brother", "younger brother", "older sister", "younger sister", "grandfather", "grandmother")
                originalWords = arrayListOf("पिता", "माता", "बेटा", "बेटी", "बड़ा भाई", "छोटा भाई", "बड़ी बहन", "छोटी बहन", "दादा", "दादी")
                images = arrayListOf(R.drawable.family_father, R.drawable.family_mother, R.drawable.family_son, R.drawable.family_daughter, R.drawable.family_older_brother,
                    R.drawable.family_younger_brother, R.drawable.family_older_sister, R.drawable.family_younger_sister, R.drawable.family_grandfather, R.drawable.family_grandmother)
                sounds = arrayListOf(R.raw.family_father, R.raw.family_mother, R.raw.family_son, R.raw.family_daughter, R.raw.family_older_brother,
                    R.raw.family_younger_brother, R.raw.family_older_sister, R.raw.family_younger_sister, R.raw.family_grandfather, R.raw.family_grandmother)
            }
            FRAGMENT_PHRASES -> {
                englishWords = arrayListOf("Where are you going?", "What is your name?", "My name is...", "How are you feeling?", "I’m feeling good.",
                    "Are you coming?", "Yes, I’m coming.", "I’m coming.", "Let’s go.", "Come here.")
                originalWords = arrayListOf("आप कहाँ जा रहे हैं?", "आपका नाम क्या है?", "मेरा नाम ... है", "आप कैसा महसूस कर रहे हैं?", "मैं अच्छा महसूस कर रहा हूँ।",
                    "क्या आप आ रहे हैं?", "हाँ, आ रहा हूं।", "मैं आ रहा हूँ।", "चलो चलते हैं।", "यहाँ आओ।")
                sounds = arrayListOf(R.raw.phrase_one, R.raw.phrase_two, R.raw.phrase_three, R.raw.phrase_four, R.raw.phrase_five,
                    R.raw.phrase_six, R.raw.phrase_seven, R.raw.phrase_eight, R.raw.phrase_nine, R.raw.phrase_ten)
            }
        }

        return Utility.mergeToTranslatedItem(englishWords = englishWords, originalWords = originalWords, images = images, sounds = sounds)
    }
}