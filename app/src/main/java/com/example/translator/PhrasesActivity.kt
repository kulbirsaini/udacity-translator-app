package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class PhrasesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishPhrases = arrayListOf("Where are you going?", "What is your name?", "My name is...", "How are you feeling?", "I’m feeling good.", "Are you coming?", "Yes, I’m coming.", "I’m coming.", "Let’s go.", "Come here.")
        val originalPhrases = arrayListOf("तुम कहाँ जा रहे हो?", "तुम्हारा नाम क्या हे?", "मेरा नाम ... है", "आप कैसा महसूस कर रहे हैं?", "मैं अच्छा महसूस कर रहा हूँ।", "क्या तुम आ रहे हो?", "हाँ, आ रहा हूं।", "मैं आ रहा हूँ।", "चलो चलते हैं।", "यहाँ आओ।")
        val phrases = Utility.mergeToTranslatedItem(englishPhrases, originalPhrases)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@PhrasesActivity)
            adapter = TranslatedItemsAdapter(phrases, R.layout.list_item, R.color.category_phrases, this@PhrasesActivity)
        }
    }
}