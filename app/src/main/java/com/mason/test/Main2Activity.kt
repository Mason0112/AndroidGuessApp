package com.mason.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mason.test.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var viewModel: GuessNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main2)
        setContentView(binding.root)
        // 初始化 ViewModel
        viewModel = ViewModelProvider(this)[GuessNumberViewModel::class.java]
        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        // 觀察 UI 訊息
        viewModel.uiMessage.observe(this) { message ->
            binding.resultText.text = message
            Log.d("MainActivity", "UI message: $message")
        }

        // 觀察提示訊息
        viewModel.hintMessage.observe(this) { hint ->
            binding.hintText.text = hint
        }

        // 觀察遊戲統計
        viewModel.gameStats.observe(this) { stats ->
            binding.statsText.text = stats
        }

        // 觀察遊戲是否進行中
        viewModel.isGameActive.observe(this) { isActive ->
            binding.guessButton.isEnabled = isActive
            binding.number.isEnabled = isActive
        }

        // 觀察猜測歷史
        viewModel.guessHistory.observe(this) { history ->
            binding.historyText.text = history
        }
    }

    private fun setupClickListeners() {
        binding.guessButton.setOnClickListener { guess(it) }
        binding.newGameButton.setOnClickListener { startNewGame() }

        // 按 Enter 鍵也可以猜測
        binding.number.setOnEditorActionListener { _, _, _ ->
            guess(binding.guessButton)
            true
        }
    }

    private fun startNewGame() {
        viewModel.startNewGame()
        binding.number.text.clear()
        binding.number.requestFocus()
    }

    fun guess(view: View) {
        val num = binding.number.text.toString()
        viewModel.makeGuess(num)

        // 清空輸入框並重新聚焦
        binding.number.text.clear()
        binding.number.requestFocus()

        Log.d("MainActivity", "guess $num")
    }
}