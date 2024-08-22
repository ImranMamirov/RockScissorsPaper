package com.example.rockscissorspaper

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val gameLogic = GameLogic()

    private val _playerChoice = MutableLiveData<Choice>()
    val playerChoice: LiveData<Choice> get() = _playerChoice

    private val _computerChoice = MutableLiveData<Choice>()
    val computerChoice: LiveData<Choice> get() = _computerChoice

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result> get() = _result

    fun playGame(playerChoice: Choice) {
        _playerChoice.value = playerChoice
        val computerChoice = gameLogic.getRandomChoice()
        _computerChoice.value = computerChoice
        _result.value = gameLogic.getGameResult(playerChoice, computerChoice)
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun resetGame() {
        _playerChoice.value = null
        _computerChoice.value = null
        _result.value = null
    }
}