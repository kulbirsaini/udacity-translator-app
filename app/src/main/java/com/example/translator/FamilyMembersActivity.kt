package com.example.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class FamilyMembersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishFamilyNames = arrayListOf("father", "mother", "son", "daughter", "older brother", "younger brother", "older sister", "younger sister", "grandfather", "grandmother")
        val originalFamilyNames = arrayListOf("पिता", "मां", "बेटा", "बेटी", "बड़ा भाई", "छोटा भाई", "बड़ी बहन", "छोटी बहन", "दादा", "दादी")
        val images = arrayListOf(R.drawable.family_father, R.drawable.family_mother, R.drawable.family_son, R.drawable.family_daughter, R.drawable.family_older_brother, R.drawable.family_younger_brother, R.drawable.family_older_sister, R.drawable.family_younger_sister, R.drawable.family_grandfather, R.drawable.family_grandmother)
        val familyMembers = Utility.mergeToTranslatedItem(englishFamilyNames, originalFamilyNames, images)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@FamilyMembersActivity)
            adapter = TranslatedItemsAdapter(familyMembers, R.layout.list_item, R.color.category_family_member, this@FamilyMembersActivity)
        }
    }
}