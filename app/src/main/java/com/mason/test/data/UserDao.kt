package com.mason.test.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // 新增使用者
    @Insert
    suspend fun insertUser(user: User): Long  // 返回新增的 id

    // 新增多個使用者
    @Insert
    suspend fun insertUsers(vararg users: User)

    // 查詢所有使用者
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    // 根據 id 查詢使用者
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    // 根據名字查詢使用者
    @Query("SELECT * FROM users WHERE name LIKE '%' || :name || '%'")
    suspend fun getUsersByName(name: String): List<User>

    // 更新使用者
    @Update
    suspend fun updateUser(user: User)

    // 刪除使用者
    @Delete
    suspend fun deleteUser(user: User)

    // 根據 id 刪除使用者
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Long)

    // 刪除所有使用者
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    // 計算使用者數量
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    // LiveData 版本 - 自動更新 UI
    @Query("SELECT * FROM users")
    fun getAllUsersLiveData(): LiveData<List<User>>

    // Flow 版本 - 更現代的做法
    @Query("SELECT * FROM users")
    fun getAllUsersFlow(): Flow<List<User>>

}