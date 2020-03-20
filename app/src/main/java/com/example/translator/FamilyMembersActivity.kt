package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_family_members.*

class FamilyMembersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_members)

        val englishFamilyNames = arrayListOf("father", "mother", "son", "daughter", "older brother", "younger brother", "older sister", "younger sister", "grandmother", "grandfather")
        val originalFamilyNames = arrayListOf("पिता", "मां", "बेटा", "बेटी", "बड़ा भाई", "छोटा भाई", "बड़ी बहन", "छोटी बहन", "दादा", "दादी")
        val familyMembers = Utility.mergeToTranslatedItem(englishFamilyNames, originalFamilyNames)

        familyMembersLayout.apply {
            layoutManager = LinearLayoutManager(this@FamilyMembersActivity)
            adapter = TranslatedItemsAdapter(familyMembers, R.layout.family_member_list)
        }
    }
}