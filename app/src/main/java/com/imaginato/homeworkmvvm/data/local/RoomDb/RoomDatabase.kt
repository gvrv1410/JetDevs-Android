package com.imaginato.homeworkmvvm.data.local.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}