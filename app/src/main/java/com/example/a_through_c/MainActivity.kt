package com.example.a_through_c

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var currentImageIndex: Int = 0;
    private val images: List<Int> = listOf(
        R.drawable.ic_android_black_24dp_100,
        R.drawable.ic_android_black_24dp_50,
        R.drawable.ic_android_black_24dp_20
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Our Shared preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val textViewText: String? = sharedPreferences.getString("TextViewText", "place_holder_text")
        currentImageIndex = sharedPreferences.getInt("ImageResourceID", 0)

        val imageView: ImageView = findViewById(R.id.imageView)
        val changeImageButton: Button = findViewById(R.id.button)
        imageView.setImageResource(images[currentImageIndex]) // Default Image

        val editText: EditText = findViewById(R.id.editText)
        val displayText: TextView = findViewById(R.id.textView2)

        displayText.text = textViewText // In case if we have some preferences saved
        editText.text = Editable.Factory.getInstance().newEditable(textViewText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                displayText.text = s.toString()
            }
        })

        changeImageButton.setOnClickListener {
            currentImageIndex += 1;
            if (currentImageIndex >= images.size) {
                currentImageIndex = 0
            }

            // Update the image
            imageView.setImageResource(images[currentImageIndex])
        }
    }

    override fun onStop() {
        super.onStop()

        val displayText: TextView = findViewById(R.id.textView2)

        val sharedPreferences: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        println("On Destroy Saved String:" + displayText.text.toString())

        val textViewText: String = displayText.text.toString()
        editor.putString("TextViewText", textViewText)

        val resourceID: Int = currentImageIndex
        editor.putInt("ImageResourceID", resourceID)

        editor.apply()
    }

    // Since the assignment demands onDestroy, but it may not be reliable...
    override fun onDestroy() {
        super.onDestroy()

        val displayText: TextView = findViewById(R.id.textView2)

        val sharedPreferences: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        println("On Destroy Saved String:" + displayText.text.toString())

        val textViewText: String = displayText.text.toString()
        editor.putString("TextViewText", textViewText)

        val resourceID: Int = currentImageIndex
        editor.putInt("ImageResourceID", resourceID)

        editor.apply()
    }
}