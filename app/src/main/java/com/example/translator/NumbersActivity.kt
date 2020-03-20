package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_numbers.*

class NumbersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers)

        val englishWords = arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        val originalWords = arrayListOf("एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस")
        val numbers = Utility.mergeToTranslatedItem(englishWords, originalWords)

        numbersLayout.apply {
            layoutManager = LinearLayoutManager(this@NumbersActivity)
            adapter = TranslatedItemsAdapter(numbers, R.layout.number_list)
        }
    }
}
