package net.storehouse.nono.mlkittranslation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var translator: FirebaseTranslator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val assetsText = getAssetsText() ?: ""
        text.text = assetsText
        prepareTranslator()
        button.setOnClickListener { translate(assetsText) }
    }

    private fun getAssetsText(): String? {
        val inputStream = assets.open("wagahai.txt")
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun prepareTranslator() {
        // Create an Japanese-English translator:
        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.JA)
            .setTargetLanguage(FirebaseTranslateLanguage.EN)
            .build()
        translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                button.visibility = View.VISIBLE
            }
            .addOnFailureListener { e ->

            }


    }

    private fun translate(beforeText: String) {
        translator.translate(beforeText)
            .addOnSuccessListener { translatedText ->
                text.text = translatedText
            }
            .addOnFailureListener { e ->

            }
    }
}
