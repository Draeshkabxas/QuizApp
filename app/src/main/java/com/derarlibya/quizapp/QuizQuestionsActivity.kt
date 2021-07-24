package com.derarlibya.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.derarlibya.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var options: MutableList<TextView>
    private lateinit var binding: ActivityQuizQuestionsBinding

    private var currentQuestion: Int = 0
    private var questionsList: List<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers:Int = 0
    private var mUserName:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName=intent.getStringExtra(Constants.USER_NAME)

        questionsList = Constants.getQuestions()

        options = ArrayList()

        setQuestion()

        setOptions()

        setOptionsOnClickListener(this)

        binding.btnSubmit.setOnClickListener {
            onSubmitClicked(it as Button)
        }
    }

    /**
     * This function check btn_submit text
     * if it is Submit call checkAnswer function
     * for check if it correct answer and change option  color
     * and call setOptionsOnClickListener to set for set all options listener to null
     * and call setSecondText to change btn_submit text
     *
     * but if it is Finish start finish activity
     * else reset the name to submit and call goToNextQuestion
     *
     *
     * @param btnSubmit the button that do all that when user click it
     */
    private fun onSubmitClicked(btnSubmit: Button) {
        when (btnSubmit.text) {
            getString(R.string.submit) -> {
                checkAnswer()
                setOptionsOnClickListener(null)
                setSecondText(btnSubmit)
            }
            getString(R.string.finish) ->{
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra(Constants.USER_NAME,mUserName)
                intent.putExtra(Constants.TOTAL_QUESTIONS,questionsList!!.size)
                intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                startActivity(intent)
                finish()
            }
            else -> {
                btnSubmit.text = getString(R.string.submit)
                goToNextQuestion()
            }
        }
    }

    /**
     * This function change btn_submit text to finish if the game finish
     * or go to next question if the game isn't finish yet
     */
    private fun setSecondText(btnSubmit: Button) {
        if (!checkQuizFinish())
            btnSubmit.text = getString(R.string.go_to_next)
        else
            btnSubmit.text = getString(R.string.finish)
    }

    /**
     * This function move to the next question
     * if the game isn't finish yet  I mean change  all  question information
     * that showing to next question information
     * if the game finish do nothing
     */
    private fun goToNextQuestion() {
        if (!checkQuizFinish()) {
            defaultOptionView()
            setOptionsOnClickListener(this)
            currentQuestion++
            setQuestion()
        }
    }

    /**
     * This function check if the game is finish
     * @return ture if there aren't questions left
     * @return false if there are questions left
     */
    private fun checkQuizFinish():Boolean =
        (currentQuestion >= questionsList!!.size - 1)


    /**
     * This function check if the answer is correct
     * if it's change answer background textView color to green
     * if not change correct answer background textView color to green
     * and selected one to red
     */
    private fun checkAnswer() {
        val mQuestion = questionsList!![currentQuestion]
        if (mQuestion.answer == mSelectedOptionPosition) {
            setRightView(getOption(mSelectedOptionPosition - 1))
            mCorrectAnswers++
        } else {
            setRightView(getOption(mQuestion.answer - 1))
            setWrongView(getOption(mSelectedOptionPosition - 1))
        }
    }

    /**
     * Set red color background to passed TextView
     * @param tv the TextView that will change it's background to red
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setWrongView(tv: TextView) {
        tv.background =
            getDrawable(R.drawable.wrong_option_border_bg)
    }

    /**
     * Set green color background to passed TextView
     * @param tv the TextView that will change it's background to green
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setRightView(tv: TextView) {
        tv.background =
            getDrawable(R.drawable.correct_option_border_bg)
    }


    /**
     * this function get the option of the index passed
     * @param index the index of the option you need
     */
    private fun getOption(index: Int): TextView = options[index]

    /**
     * This function set all options listener to be
     * the parameter listener
     * @param listener is the listener the will be set to all options
     */
    private fun setOptionsOnClickListener(listener: View.OnClickListener?) {
        options.forEach { options ->
            options.setOnClickListener(listener)
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
            }
        }
    }

    /**
     * This method make option like selected
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun selectedOptionView(
        tv: TextView, selectedOptionNum: Int
    ) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.background = getDrawable(R.drawable.select_option_border_bg)
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)

    }


}