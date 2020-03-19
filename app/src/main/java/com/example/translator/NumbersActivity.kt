package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_numbers.*

class NumbersActivity : AppCompatActivity() {

    private val englishWords = arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
    private val originalWords = arrayListOf("एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस")
    private var numbers = arrayListOf<TranslatedItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers)

        englishWords.zip(originalWords).forEach {
            numbers.add(TranslatedItem(it.first, it.second))
        }

        numbersLayout.apply {
            layoutManager = LinearLayoutManager(this@NumbersActivity)
            adapter = TranslatedItemsAdapter(numbers, R.layout.number_list)
        }
    }
}
