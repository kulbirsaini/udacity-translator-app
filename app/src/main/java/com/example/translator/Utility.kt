package com.example.translator

class Utility {
    companion object {
        fun mergeToTranslatedItem(
            englishWords: ArrayList<String>,
            originalWords: ArrayList<String>
        ): ArrayList<TranslatedItem> {
            val items = ArrayList<TranslatedItem>()
            englishWords.zip(originalWords).map {
                items.add(TranslatedItem(it.first, it.second))
            }

            return items
        }
    }
}