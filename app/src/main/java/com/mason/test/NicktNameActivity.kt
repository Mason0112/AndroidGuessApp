package com.mason.test

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mason.test.data.User
import com.mason.test.data.UserAdapter
import com.mason.test.data.UserViewModel
import com.mason.test.databinding.ActivityNicktNameBinding

class NicktNameActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityNicktNameBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicktNameBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupRecyclerView()
        observeData()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.sendButton.setOnClickListener {
            val nameEdit = binding.editUserNameText
            val name = nameEdit.text.toString().trim()
            if (name.isNotEmpty()) {
                userViewModel.insertUser(name)
                nameEdit.text.clear()
            } else {
                Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show()
            }
        }

        binding.editUserNameText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.sendButton.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun observeData() {
        userViewModel.allUsers.observe(this) { users ->
            userAdapter.submitList(users)
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            handleUserClick(user)
        }

        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@NicktNameActivity)
        }
    }

    private fun handleUserClick(user: User) {
        AlertDialog.Builder(this)
            .setTitle("選擇操作")
            .setItems(arrayOf("編輯", "刪除")) { _, which ->
                when (which) {
                    0 -> editUser(user)
                    1 -> deleteUser(user)
                }
            }
            .show()
    }

    private fun editUser(user: User) {
        val editText = EditText(this)
        editText.setText(user.name)

        AlertDialog.Builder(this)
            .setTitle("編輯使用者")
            .setView(editText)
            .setPositiveButton("確定") { _, _ ->
                val newName = editText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    user.name = newName
                    userViewModel.updateUser(user)
                } else {
                    Toast.makeText(this, "姓名不能為空", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun deleteUser(user: User) {
        AlertDialog.Builder(this)
            .setTitle("刪除使用者")
            .setMessage("確定要刪除 ${user.name} 嗎？")
            .setPositiveButton("確定") { _, _ ->
                userViewModel.deleteUser(user)
            }
            .setNegativeButton("取消", null)
            .show()
    }
}