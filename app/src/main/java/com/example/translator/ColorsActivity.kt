package com.example.translator

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class ColorsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishColors = arrayListOf("red", "green", "brown", "gray", "black", "white", "dusty yellow", "mustard yellow")
        val originalColors = arrayListOf("लाल", "हरा", "भूरा", "स्लेटी", "काला", "सफेद", "धूल भरा पीला", "सरसों का पीला")
        val images = arrayListOf(R.drawable.color_red, R.drawable.color_green, R.drawable.color_brown, R.drawable.color_gray, R.drawable.color_black, R.drawable.color_white, R.drawable.color_dusty_yellow, R.drawable.color_mustard_yellow)
        val sounds = arrayListOf(R.raw.color_red, R.raw.color_green, R.raw.color_brown, R.raw.color_gray, R.raw.color_black, R.raw.color_white, R.raw.color_dusty_yellow, R.raw.color_mustard_yellow)
        val colors = Utility.mergeToTranslatedItem(englishColors, originalColors, images, sounds)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@ColorsActivity)
            adapter = TranslatedItemsAdapter(colors, R.color.category_colors, this@ColorsActivity, ColorsActivity::class.java.simpleName)
        }
    }
}
