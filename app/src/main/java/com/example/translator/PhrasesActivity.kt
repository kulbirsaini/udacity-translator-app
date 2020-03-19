package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_phrases.*

class PhrasesActivity : AppCompatActivity() {

    private val englishPhrases = arrayListOf("Where are you going?", "What is your name?", "My name is...", "How are you feeling?", "I’m feeling good.", "Are you coming?", "Yes, I’m coming.", "I’m coming.", "Let’s go.", "Come here.")
    private val originalPhrases = arrayListOf("तुम कहाँ जा रहे हो?", "तुम्हारा नाम क्या हे?", "मेरा नाम ... है", "आप कैसा महसूस कर रहे हैं?", "मैं अच्छा महसूस कर रहा हूँ।", "क्या तुम आ रहे हो?", "हाँ, आ रहा हूं।", "मैं आ रहा हूँ।", "चलो चलते हैं।", "यहाँ आओ।")
    private val phrases = ArrayList<TranslatedItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrases)

        englishPhrases.zip(originalPhrases).forEach {
            phrases.add(TranslatedItem(it.first, it.second))
        }

        phrasesLayout.apply {
            layoutManager = LinearLayoutManager(this@PhrasesActivity)
            adapter = TranslatedItemsAdapter(phrases, R.layout.phrase_list)
        }
    }
}