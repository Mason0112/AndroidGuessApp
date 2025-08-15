package com.mason.test.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    // LiveData
    val allUsers: LiveData<List<User>> = userDao.getAllUsersLiveData()

    // Flow
    val allUsersFlow: Flow<List<User>> = userDao.getAllUsersFlow()

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun searchUsersByName(name: String): List<User> {
        return userDao.getUsersByName(name)
    }
}