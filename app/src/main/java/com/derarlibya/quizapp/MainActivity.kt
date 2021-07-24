package com.derarlibya.quizapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.derarlibya.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //makeFullScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AndroidBug5497Workaround.assistActivity(this)

        //when user click on start button run onStartClicked function
        binding.btnStart.setOnClickListener { view ->
            onStartClicked(view )
        }

    }

    /**
     * this method check if user add a name
     * if them add one then start new activity and finish this activity.
     * if not it show toast Please enter your name. and do nothing else.
     */
    private fun onStartClicked(view: View){
        if(binding.etName.text.isNullOrEmpty()){
            Toast.makeText(this,"Please enter your name.",Toast.LENGTH_SHORT).show()
            return
        }
        val intent=Intent(this,QuizQuestionsActivity::class.java)

        intent.putExtra(Constants.USER_NAME,binding.etName.text.toString())
        startActivity(intent)
        finish()
    }
}