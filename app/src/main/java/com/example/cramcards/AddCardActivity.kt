package com.example.cramcards

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val cancelButton = findViewById<ImageView>(R.id.cancel_button)
        val saveButton = findViewById<ImageView>(R.id.save_button)
        val questionEditText = findViewById<EditText>(R.id.add_question)
        val answerEditText = findViewById<EditText>(R.id.add_answer)



        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener{
            val questionString = questionEditText.text.toString()
            val answerString = answerEditText.text.toString()
            val data = Intent()
            data.putExtra("QUESTION_KEY", questionString)
            data.putExtra("ANSWER_KEY", answerString)

            setResult(RESULT_OK, data)
            finish()
        }



    }
}