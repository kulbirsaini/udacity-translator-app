package com.example.translator

class Utility {
    companion object {
        fun mergeToTranslatedItem(
            englishWords: ArrayList<String>,
            originalWords: ArrayList<String>,
            images: ArrayList<Int> = ArrayList(),
            sounds: ArrayList<Int> = ArrayList()
        ): ArrayList<TranslatedItem> {
            val items = ArrayList<TranslatedItem>()
            val maxImageIndex = images.size - 1
            val maxSoundIndex = sounds.size - 1

            for (index in 0 until englishWords.size) {
                val image = if (index <= maxImageIndex) images[index] else 0
                val sound = if (index <= maxSoundIndex) sounds[index] else 0

                items.add(TranslatedItem(englishWords[index], originalWords[index], image, sound))
            }

            return items
        }
    }
}