package com.example.rockscissorspaper

class GameLogic {
    fun getGameResult(playerChoice: Choice, computerChoice: Choice): Result {
        return when {
            playerChoice == computerChoice -> Result.DRAW
            (playerChoice == Choice.ROCK && computerChoice == Choice.SCISSORS) ||
                    (playerChoice == Choice.SCISSORS && computerChoice == Choice.PAPER) ||
                    (playerChoice == Choice.PAPER && computerChoice == Choice.ROCK) -> Result.WIN
            else -> Result.LOSE
        }
    }

    fun getRandomChoice(): Choice {
        return Choice.entries.toTypedArray().random()
    }
}

enum class Choice {
    ROCK, PAPER, SCISSORS
}

enum class Result {
    WIN, LOSE, DRAW
}