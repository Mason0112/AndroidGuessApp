package com.mason.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class User (
    var name : String,
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
)