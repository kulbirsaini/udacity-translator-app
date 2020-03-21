package com.example.translator

class Utility {
    companion object {
        fun mergeToTranslatedItem(
            englishWords: ArrayList<String>,
            originalWords: ArrayList<String>,
            images: ArrayList<Int> = ArrayList()
        ): ArrayList<TranslatedItem> {
            val items = ArrayList<TranslatedItem>()

            if (images.size == 0) {
                englishWords.zip(originalWords).forEach {
                    items.add(TranslatedItem(it.first, it.second))
                }
            } else {
                englishWords.zip(originalWords).zip(images).forEach {
                    items.add(TranslatedItem(it.first.first, it.first.second, it.second))
                }
            }

            return items
        }
    }
}