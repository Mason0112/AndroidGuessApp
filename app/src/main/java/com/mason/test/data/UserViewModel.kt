package com.mason.test.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel (application: Application) : AndroidViewModel(application){

    private val repository: UserRepository


    // LiveData 用於觀察資料變化
    val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    // 新增使用者
    fun insertUser(name: String) = viewModelScope.launch {
        val user = User(name = name)
        repository.insertUser(user)
    }

    // 更新使用者
    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    // 刪除使用者
    fun deleteUser(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    // 搜尋使用者
    fun searchUsers(name: String): LiveData<List<User>> {
        return liveData {
            emit(repository.searchUsersByName(name))
        }
    }

}