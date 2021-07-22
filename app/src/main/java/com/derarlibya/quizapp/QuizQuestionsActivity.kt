package com.derarlibya.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.derarlibya.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuizQuestionsBinding
    var index=4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionsList = Constants.getQuestions()

        questionsList.forEach { question ->
            Log.i("QuestionsList ", "$question")
        }
        show(questionsList)

    }


    private fun show(questionsList:List<Question>){
        val currentQuestion=questionsList[index]

        //Set question textView to be the question of currentQuestion
        binding.tvQuestion.text=currentQuestion.question

        //Set image ImageView to be the image of currentQuestion
        binding.ivImage.setImageResource( currentQuestion.image)

        // index start from 0 but progress should start from 1
        val currentProgress=index+1
        //Set progress progressBar to be currentProgress

        binding.progressBar.progress=currentProgress

        //Set progress text TextView to be currentProgress/how many questions
        binding.tvProgress.text="$currentProgress/${questionsList.size}"


        //Set option questions textView to be the questions of currentQuestion
        binding.tvOptionOne.text=currentQuestion.optionOne
        binding.tvOptionTwo.text=currentQuestion.optionTwo
        binding.tvOptionThree.text=currentQuestion.optionThree
        binding.tvOptionFour.text=currentQuestion.optionFour

    }


}