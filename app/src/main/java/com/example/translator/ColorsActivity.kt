package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_colors.*

class ColorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors)

        val englishColors = arrayListOf("red", "green", "brown", "gray", "black", "white", "dusty yellow", "mustard yellow")
        val originalColors = arrayListOf("लाल", "हरा", "भूरा", "धूसर", "काला", "सफेद", "धूल भरा पीला", "सरसों का पीला")
        val colors = Utility.mergeToTranslatedItem(englishColors, originalColors)

        colorsLayout.apply {
            layoutManager = LinearLayoutManager(this@ColorsActivity)
            adapter = TranslatedItemsAdapter(colors, R.layout.color_list)
        }
    }
}
