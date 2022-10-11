package com.example.cramcards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

lateinit var flashcardDatabase: FlashcardDatabase
var allFlashcards = mutableListOf<Flashcard>()

class MainActivity : AppCompatActivity() {

    private lateinit var flashcardDatabase: FlashcardDatabase
    private var allFlashcards = mutableListOf<Flashcard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val multipleChoice2 = findViewById<TextView>(R.id.answer2)
        val multipleChoice1 = findViewById<TextView>(R.id.answer1)
        val multipleChoice3 = findViewById<TextView>(R.id.answer3)
        val hideIcon = findViewById<ImageView>(R.id.toggle_choices_visibility)
        val addQuestionButton = findViewById<ImageView>(R.id.add_question_button)
        val editQuestionButton = findViewById<ImageView>(R.id.edit_question_button)
        val nextQuestionButton = findViewById<ImageView>(R.id.next_question_button)
        val deleteQuestionButton = findViewById<ImageView>(R.id.delete_question_button)
        var currentCardDisplayedIndex = 0

        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }


        var isShowingAnswers = true
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            val data: Intent? = result.data

            if(data != null) {
                val questionString = data.getStringExtra("QUESTION_KEY")
                val answerString = data.getStringExtra("ANSWER_KEY")

                flashcardQuestion.text = questionString
                flashcardAnswer.text = answerString

                if (questionString != null && answerString != null) {


                    flashcardDatabase.insertCard(
                        Flashcard(
                            questionString.toString(),
                            answerString.toString()
                        )
                    )

                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("MainActivity", "Missing question or answer input. Question is $questionString and answer is $answerString")
                }

                Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card created!",
                    Snackbar.LENGTH_SHORT)
                    .show()



            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }

        }


        hideIcon.setOnClickListener{
            if (isShowingAnswers){
                multipleChoice1.visibility = View.INVISIBLE
                multipleChoice2.visibility = View.INVISIBLE
                multipleChoice3.visibility = View.INVISIBLE
                hideIcon.setImageResource(R.drawable.show_icon)
                isShowingAnswers = false}
            else {
                multipleChoice1.visibility = View.VISIBLE
                multipleChoice2.visibility = View.VISIBLE
                multipleChoice3.visibility = View.VISIBLE
                hideIcon.setImageResource(R.drawable.hide_icon)
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

        addQuestionButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        editQuestionButton.setOnClickListener {
            val currentQuestionString = flashcardQuestion.text.toString()
            val currentAnswerString = flashcardAnswer.text.toString()
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("CURRENT_QUESTION_KEY", currentQuestionString)
            intent.putExtra("CURRENT_ANSWER_KEY", currentAnswerString)
            resultLauncher.launch(intent)
        }

        nextQuestionButton.setOnClickListener {
            if (allFlashcards.isEmpty()) {
                return@setOnClickListener
            }
            currentCardDisplayedIndex++

            if(currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    flashcardQuestion,
                    "You've reached the end of the cards, going back to start",
                    Snackbar.LENGTH_SHORT).show()
                currentCardDisplayedIndex = 0
            }
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer) = allFlashcards[currentCardDisplayedIndex]

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
        }

        deleteQuestionButton.setOnClickListener {

            val flashcardQuestionToDelete = flashcardQuestion.text.toString()
            flashcardDatabase.deleteCard(flashcardQuestionToDelete)


            currentCardDisplayedIndex++

            if(currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    flashcardQuestion,
                    "You've reached the end of the cards, going back to start",
                    Snackbar.LENGTH_SHORT).show()
                currentCardDisplayedIndex = 0
            }










        }


    }
}