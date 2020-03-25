package com.example.translator

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class PhrasesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishPhrases = arrayListOf("Where are you going?", "What is your name?", "My name is...", "How are you feeling?", "I’m feeling good.", "Are you coming?", "Yes, I’m coming.", "I’m coming.", "Let’s go.", "Come here.")
        val originalPhrases = arrayListOf("आप कहाँ जा रहे हैं?", "आपका नाम क्या है?", "मेरा नाम ... है", "आप कैसा महसूस कर रहे हैं?", "मैं अच्छा महसूस कर रहा हूँ।", "क्या आप आ रहे हैं?", "हाँ, आ रहा हूं।", "मैं आ रहा हूँ।", "चलो चलते हैं।", "यहाँ आओ।")
        val sounds = arrayListOf(R.raw.phrase_one, R.raw.phrase_two, R.raw.phrase_three, R.raw.phrase_four, R.raw.phrase_five, R.raw.phrase_six, R.raw.phrase_seven, R.raw.phrase_eight, R.raw.phrase_nine, R.raw.phrase_ten)
        val phrases = Utility.mergeToTranslatedItem(englishWords = englishPhrases, originalWords = originalPhrases, sounds = sounds)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@PhrasesActivity)
            adapter = TranslatedItemsAdapter(phrases, R.color.category_phrases,this@PhrasesActivity, PhrasesActivity::class.java.simpleName)
        }
    }
}