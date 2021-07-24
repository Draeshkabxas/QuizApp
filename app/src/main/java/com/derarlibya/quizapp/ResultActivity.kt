package com.derarlibya.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.derarlibya.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Get user information form intent
        val userName=intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions:Int=intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswers:Int=intent.getIntExtra(Constants.CORRECT_ANSWERS,0)

        //show user result
        setResult(userName!!,totalQuestions,correctAnswers)

        //Go to main activity when user click finish button
        binding.btnFinish.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    /**
     * This function show to user the result of this quiz
     */
    @SuppressLint("SetTextI18n")
    private fun setResult(userName: String, totalQuestions: Int, correctAnswers: Int) {
        binding.tvName.text = userName
        binding.tvScore.text="Your score is $correctAnswers out of $totalQuestions"
    }
}