package com.derarlibya.quizapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.derarlibya.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var options: MutableList<TextView>
    private lateinit var binding: ActivityQuizQuestionsBinding

    private var currentQuestion: Int = 4
    private var questionsList: List<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionsList = Constants.getQuestions()

        options = ArrayList()

        setQuestion()

        setOptions()

        setOptionsOnClickListener()
    }

    /**
     * This function set all options listener to be
     * the listener of  this activity
     */
    private fun setOptionsOnClickListener() {
        options.forEach { options ->
            options.setOnClickListener(this)
        }
    }


    /**
     * This method set all question views Information of this activity to
     * be the question in questionsList at index of currentQuestion
     */
    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val mQuestion = questionsList!![currentQuestion]

        //Set all option to the default
        defaultOptionView()

        //Set question textView to be the question of mQuestion
        binding.tvQuestion.text = mQuestion.question

        //Set image ImageView to be the image of mQuestion
        binding.ivImage.setImageResource(mQuestion.image)

        // index start from 0 but progress should start from 1
        val currentProgress = currentQuestion + 1
        //Set progress progressBar to be mQuestion

        binding.progressBar.progress = currentProgress

        //Set progress text TextView to be mQuestion/how many questions
        binding.tvProgress.text = "$currentProgress/${questionsList!!.size}"


        //Set option questions textView to be the questions of mQuestion
        binding.tvOptionOne.text = mQuestion.optionOne
        binding.tvOptionTwo.text = mQuestion.optionTwo
        binding.tvOptionThree.text = mQuestion.optionThree
        binding.tvOptionFour.text = mQuestion.optionFour

    }

    /**
     * Add all options that we have inside options list
     */
    private fun setOptions() {
        options.add(binding.tvOptionOne)
        options.add(binding.tvOptionTwo)
        options.add(binding.tvOptionThree)
        options.add(binding.tvOptionFour)
    }

    /**
     * Reset all options as default
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun defaultOptionView() {
        options.forEach { option ->
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = getDrawable(R.drawable.default_option_border_bg)
        }
    }

    /**
     * This function implement from OnClickListener to
     * make it easy to select option
     */
    override fun onClick(p0: View?) {
        for (i in 0 until options.size) {
            if (options[i] == p0) {
                selectedOptionView(p0 as TextView, i + 1)
                Log.i(this.localClassName, "Selected option is $i")
            }
        }
    }

    /**
     * This method make option like selected
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun selectedOptionView(tv: TextView
                                   , selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.background = getDrawable(R.drawable.select_option_border_bg)
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)

    }


}