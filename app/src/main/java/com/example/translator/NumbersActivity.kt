package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class NumbersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishWords = arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        val originalWords = arrayListOf("एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस")
        val numberImages = arrayListOf(R.drawable.number_one, R.drawable.number_two, R.drawable.number_three, R.drawable.number_four, R.drawable.number_five, R.drawable.number_six, R.drawable.number_seven, R.drawable.number_eight, R.drawable.number_nine, R.drawable.number_ten)
        val numbers = Utility.mergeToTranslatedItem(englishWords, originalWords, numberImages)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@NumbersActivity)
            adapter = TranslatedItemsAdapter(numbers, R.layout.list_item, R.color.category_numbers)
        }
    }
}
