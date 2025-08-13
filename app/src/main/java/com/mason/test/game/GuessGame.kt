package com.mason.test.game

class GuessGame {

    enum class GameResult {
        TOO_BIG, TOO_SMALL, CORRECT,GAME_OVER,INVALID_INPUT,ALREADY_GUESSED
    }

    // 遊戲設定
    private val maxAttempts = 10
    private val minNumber = 1
    private val maxNumber = 100

    // 遊戲狀態
    private var targetNumber: Int = 0
    private var guessCount: Int = 0
    private var isGameOver: Boolean = false
    private val guessHistory = mutableSetOf<Int>()

    init {
        startNewGame()
    }
    // 開始新遊戲
    fun startNewGame() {
        targetNumber = (minNumber..maxNumber).random()
        guessCount = 0
        isGameOver = false
        guessHistory.clear()
    }

    // 處理猜測 - 純粹的邏輯，不涉及 UI
    fun makeGuess(guess: Int): GameResult {
        // 檢查遊戲是否已結束
        if (isGameOver) {
            return GameResult.GAME_OVER
        }

        // 檢查輸入範圍
        if (guess < minNumber || guess > maxNumber) {
            return GameResult.INVALID_INPUT
        }

        // 檢查是否已經猜過
        if (guessHistory.contains(guess)) {
            return GameResult.ALREADY_GUESSED
        }

        // 記錄猜測
        guessHistory.add(guess)
        guessCount++

        // 判斷結果
        return when {
            guess == targetNumber -> {
                isGameOver = true
                GameResult.CORRECT
            }
            guessCount >= maxAttempts -> {
                isGameOver = true
                GameResult.GAME_OVER
            }
            guess < targetNumber -> GameResult.TOO_SMALL
            else -> GameResult.TOO_BIG
        }
    }

    // Getter 方法
    fun getTargetNumber(): Int = targetNumber
    fun getGuessCount(): Int = guessCount
    fun getRemainingAttempts(): Int = maxAttempts - guessCount
    fun isGameFinished(): Boolean = isGameOver
    fun getGuessHistory(): Set<Int> = guessHistory.toSet()
    fun getMinNumber(): Int = minNumber
    fun getMaxNumber(): Int = maxNumber
    fun getMaxAttempts(): Int = maxAttempts
}