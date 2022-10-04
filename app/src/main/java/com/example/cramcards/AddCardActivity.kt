package com.example.cramcards

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val cancelButton = findViewById<ImageView>(R.id.cancel_button)
        val saveButton = findViewById<ImageView>(R.id.save_button)
        val questionEditText = findViewById<EditText>(R.id.add_question)
        val answerEditText = findViewById<EditText>(R.id.add_answer)
        val s1 = intent.getStringExtra("CURRENT_QUESTION_KEY")
        val s2 = intent.getStringExtra("CURRENT_ANSWER_KEY")

        if (s1 != null) {
            questionEditText.setText(s1)
        }

        if (s2 != null) {
            answerEditText.setText(s2)
        }

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener{
            val questionString = questionEditText.text.toString()
            val answerString = answerEditText.text.toString()
            val data = Intent()

            if ((questionString.isEmpty()) || (answerString.isEmpty())) {
                Toast.makeText(applicationContext, "You must complete both question and answer fields!", Toast.LENGTH_SHORT).show()
            }

            else {
            data.putExtra("QUESTION_KEY", questionString)
            data.putExtra("ANSWER_KEY", answerString)

            setResult(RESULT_OK, data)
            finish()
            }
        }



    }
}