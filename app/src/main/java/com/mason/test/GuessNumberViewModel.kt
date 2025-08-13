package com.mason.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mason.test.game.GuessGame

class GuessNumberViewModel : ViewModel() {

    // 遊戲實例
    private val game = GuessGame()

    // UI 狀態的 LiveData
    private val _uiMessage = MutableLiveData<String>()
    private val _hintMessage = MutableLiveData<String>()
    private val _gameStats = MutableLiveData<String>()
    private val _isGameActive = MutableLiveData<Boolean>()
    private val _guessHistory = MutableLiveData<String>()

    // 對外公開的 LiveData
    val uiMessage: LiveData<String> = _uiMessage
    val hintMessage: LiveData<String> = _hintMessage
    val gameStats: LiveData<String> = _gameStats
    val isGameActive: LiveData<Boolean> = _isGameActive
    val guessHistory: LiveData<String> = _guessHistory

    init {
        updateUIAfterGameStart()
    }

    // 處理用戶猜測
    fun makeGuess(guessText: String) {
        // 輸入驗證
        val guess = guessText.toIntOrNull()
        if (guess == null) {
            _uiMessage.value = "請輸入有效的數字！"
            return
        }

        // 調用遊戲邏輯
        val result = game.makeGuess(guess)

        // 根據結果更新 UI 狀態
        updateUIBasedOnResult(result, guess)
        updateGameStats()
        updateGuessHistory()
    }

    // 開始新遊戲
    fun startNewGame() {
        game.startNewGame()
        updateUIAfterGameStart()
    }

    // 根據遊戲結果更新 UI
    private fun updateUIBasedOnResult(result: GuessGame.GameResult, guess: Int) {
        when (result) {
            GuessGame.GameResult.CORRECT -> {
                _uiMessage.value = "🎉 恭喜你猜對了！答案是 ${game.getTargetNumber()}"
                _hintMessage.value = "你總共猜了 ${game.getGuessCount()} 次"
                _isGameActive.value = false
            }
            GuessGame.GameResult.TOO_SMALL -> {
                _uiMessage.value = "太小了！再試試看"
                _hintMessage.value = "提示：答案比 $guess 大 (剩餘 ${game.getRemainingAttempts()} 次機會)"
            }
            GuessGame.GameResult.TOO_BIG -> {
                _uiMessage.value = "太大了！再試試看"
                _hintMessage.value = "提示：答案比 $guess 小 (剩餘 ${game.getRemainingAttempts()} 次機會)"
            }
            GuessGame.GameResult.GAME_OVER -> {
                _uiMessage.value = "😢 很遺憾，你已用完所有機會！答案是 ${game.getTargetNumber()}"
                _hintMessage.value = "遊戲結束"
                _isGameActive.value = false
            }
            GuessGame.GameResult.INVALID_INPUT -> {
                _uiMessage.value = "請輸入 ${game.getMinNumber()} 到 ${game.getMaxNumber()} 之間的數字！"
            }
            GuessGame.GameResult.ALREADY_GUESSED -> {
                _uiMessage.value = "你已經猜過 $guess 了！"
            }
        }
    }

    // 遊戲開始後更新 UI
    private fun updateUIAfterGameStart() {
        _hintMessage.value = "請猜一個 ${game.getMinNumber()} 到 ${game.getMaxNumber()} 之間的數字"
        _uiMessage.value = ""
        _isGameActive.value = true
        updateGameStats()
        _guessHistory.value = ""
    }

    // 更新遊戲統計
    private fun updateGameStats() {
        _gameStats.value = "已猜測: ${game.getGuessCount()} 次 | 剩餘: ${game.getRemainingAttempts()} 次"
    }

    // 更新猜測歷史
    private fun updateGuessHistory() {
        val history = game.getGuessHistory()
        if (history.isNotEmpty()) {
            _guessHistory.value = "猜測記錄: ${history.sorted().joinToString(", ")}"
        }
    }
}