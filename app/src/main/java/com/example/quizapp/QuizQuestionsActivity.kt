package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    private lateinit var binding: ActivityQuizQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.getQuestions()

        setQuestion()
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        Log.i("Questions size", "${mQuestionList!!.size}")
Log.i("progress bar max" , "${binding.progressBar.max}")

    }

    private fun setQuestion(){

        val question =  mQuestionList!![mCurrentPosition - 1]
        defaultOptionsView()

        if(mCurrentPosition == mQuestionList!!.size){
            binding.btnSubmit.text = "Finish"
        }else {
            binding.btnSubmit.text = "Submit"
        }

        binding.progressBar.progress = mCurrentPosition

        binding.tvProgress.text = "${mCurrentPosition}" + "/" + binding.progressBar.max
        binding.tvQuestion.text = question!!.question
        binding.ivImage.setImageResource(question!!.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options){
            option.setTextColor(Color.parseColor(("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one -> {
                selectedOptionView( binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView( binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView( binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView( binding.tvOptionFour, 4)
            }

            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0){
                    //go to next question
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else{
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option)

                    if(mCurrentPosition == mQuestionList!!.size){
                        binding.btnSubmit.text = "Finish"
                    } else{
                        binding.btnSubmit.text = "Next question"
                    }
                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1->{
                binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2->{
                binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3->{
                binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4->{
                binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, SelectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = SelectedOptionNum

        tv.setTextColor(Color.parseColor(("#363A43")))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option)
    }

}