package com.example.translator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numbersTab = findViewById<TextView>(R.id.numbersTab)
        val familyMembersTab = findViewById<TextView>(R.id.familyMembersTab)
        val colorsTab = findViewById<TextView>(R.id.colorsTab)
        val phrasesTab = findViewById<TextView>(R.id.phrasesTab)

        numbersTab.setOnClickListener(this)
        familyMembersTab.setOnClickListener(this)
        colorsTab.setOnClickListener(this)
        phrasesTab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v?.id) {
            R.id.numbersTab -> intent = Intent(this, NumbersActivity::class.java)
            R.id.familyMembersTab -> intent = Intent(this, FamilyMembersActivity::class.java)
            R.id.colorsTab -> intent = Intent(this, ColorsActivity::class.java)
            R.id.phrasesTab -> intent = Intent(this, PhrasesActivity::class.java)
        }

        if (intent != null) {
            startActivity(intent)
        }
    }
}
