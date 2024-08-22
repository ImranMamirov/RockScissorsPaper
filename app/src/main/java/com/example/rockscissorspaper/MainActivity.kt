package com.example.rockscissorspaper

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var resultTextView: TextView
    private lateinit var playerChoiceTextView: TextView
    private lateinit var computerChoiceTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var restartButton: Button

    private lateinit var rockButton: Button
    private lateinit var paperButton: Button
    private lateinit var scissorsButton: Button

    private var countDownTimer: CountDownTimer? = null
    private var isPlayerTurn = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        playerChoiceTextView = findViewById(R.id.playerChoiceTextView)
        computerChoiceTextView = findViewById(R.id.computerChoiceTextView)
        timerTextView = findViewById(R.id.timerTextView)
        restartButton = findViewById(R.id.restartButton)

        rockButton = findViewById(R.id.rockButton)
        paperButton = findViewById(R.id.paperButton)
        scissorsButton = findViewById(R.id.scissorsButton)

        rockButton.setOnClickListener { onPlayerChoice(Choice.ROCK, rockButton) }
        paperButton.setOnClickListener { onPlayerChoice(Choice.PAPER, paperButton) }
        scissorsButton.setOnClickListener { onPlayerChoice(Choice.SCISSORS, scissorsButton) }

        restartButton.setOnClickListener { restartGame() }

        viewModel.playerChoice.observe(this, Observer { choice ->
            playerChoiceTextView.text = "Your choice: $choice"
        })

        viewModel.computerChoice.observe(this, Observer { choice ->
            computerChoiceTextView.text = "Computer's choice: $choice"
        })

        viewModel.result.observe(this, Observer { result ->
            resultTextView.text = "Result: $result"
        })

        startNewRound()
    }

    @SuppressLint("SetTextI18n")
    private fun startNewRound() {
        isPlayerTurn = true
        resultTextView.text = ""
        playerChoiceTextView.text = "Your choice: "
        computerChoiceTextView.text = "Computer's choice: "
        startTimer()
    }

    @SuppressLint("SetTextI18n")
    private fun startTimer() {
        timerTextView.text = "5"
        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = (millisUntilFinished / 1000).toString()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                if (isPlayerTurn) {
                    viewModel.playGame(Choice.entries.toTypedArray().random())
                    resultTextView.text = "Time is up! Random choice selected."
                }
            }
        }.start()
    }

    private fun onPlayerChoice(choice: Choice, button: Button) {
        if (isPlayerTurn) {
            isPlayerTurn = false
            countDownTimer?.cancel()
            startButtonScaleAnimation(button)
            viewModel.playGame(choice)
        }
    }

    private fun startButtonScaleAnimation(button: Button) {
        val scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        button.startAnimation(scaleUpAnimation)
    }

    @SuppressLint("SetTextI18n")
    private fun restartGame() {
        viewModel.resetGame()
        resultTextView.text = ""
        playerChoiceTextView.text = "Your choice: "
        computerChoiceTextView.text = "Computer's choice: "
        resetButtonSize(rockButton)
        resetButtonSize(paperButton)
        resetButtonSize(scissorsButton)
        startNewRound()
    }

    private fun resetButtonSize(button: Button) {
        button.scaleX = 1f
        button.scaleY = 1f
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}