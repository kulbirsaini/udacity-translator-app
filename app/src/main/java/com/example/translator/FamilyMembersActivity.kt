package com.example.translator

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_container.*

class FamilyMembersActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        val englishFamilyNames = arrayListOf("father", "mother", "son", "daughter", "older brother", "younger brother", "older sister", "younger sister", "grandfather", "grandmother")
        val originalFamilyNames = arrayListOf("पिता", "माता", "बेटा", "बेटी", "बड़ा भाई", "छोटा भाई", "बड़ी बहन", "छोटी बहन", "दादा", "दादी")
        val images = arrayListOf(R.drawable.family_father, R.drawable.family_mother, R.drawable.family_son, R.drawable.family_daughter, R.drawable.family_older_brother, R.drawable.family_younger_brother, R.drawable.family_older_sister, R.drawable.family_younger_sister, R.drawable.family_grandfather, R.drawable.family_grandmother)
        val sounds = arrayListOf(R.raw.family_father, R.raw.family_mother, R.raw.family_son, R.raw.family_daughter, R.raw.family_older_brother, R.raw.family_younger_brother, R.raw.family_older_sister, R.raw.family_younger_sister, R.raw.family_grandfather, R.raw.family_grandmother)
        val familyMembers = Utility.mergeToTranslatedItem(englishFamilyNames, originalFamilyNames, images, sounds)

        activityLayout.apply {
            layoutManager = LinearLayoutManager(this@FamilyMembersActivity)
            adapter = TranslatedItemsAdapter(familyMembers, R.color.category_family_member, this@FamilyMembersActivity, FamilyMembersActivity::class.java.simpleName)
        }
    }
}