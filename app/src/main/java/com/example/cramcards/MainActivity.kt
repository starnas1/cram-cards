package com.example.cramcards

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val multipleChoice2 = findViewById<TextView>(R.id.answer2)
        val multipleChoice1 = findViewById<TextView>(R.id.answer1)
        val multipleChoice3 = findViewById<TextView>(R.id.answer3)
        val hideIcon = findViewById<ImageView>(R.id.toggle_choices_visibility)
        var isShowingAnswers = true


        hideIcon.setOnClickListener{
            if (isShowingAnswers){
                multipleChoice1.visibility = View.INVISIBLE
                multipleChoice2.visibility = View.INVISIBLE
                multipleChoice3.visibility = View.INVISIBLE
                findViewById<ImageView>(R.id.toggle_choices_visibility).setImageResource(R.drawable.show_icon)
                isShowingAnswers = false}
            else {
                multipleChoice1.visibility = View.VISIBLE
                multipleChoice2.visibility = View.VISIBLE
                multipleChoice3.visibility = View.VISIBLE
                findViewById<ImageView>(R.id.toggle_choices_visibility).setImageResource(R.drawable.hide_icon)
                isShowingAnswers = true}
        }

        flashcardQuestion.setOnClickListener {
            it.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }
        flashcardAnswer.setOnClickListener {
            flashcardQuestion.visibility = View.VISIBLE
            it.visibility = View.INVISIBLE
        }
        multipleChoice2.setOnClickListener{
        it.setBackgroundColor(resources.getColor(R.color.green_right))
        }
        multipleChoice1.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.red_wrong))
            multipleChoice1.setTextColor(resources.getColor(R.color.beige))
            multipleChoice2.setBackgroundColor(resources.getColor(R.color.green_right))
        }
        multipleChoice3.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.red_wrong))
            multipleChoice3.setTextColor(resources.getColor(R.color.beige))
            multipleChoice2.setBackgroundColor(resources.getColor(R.color.green_right))
        }
    }
}