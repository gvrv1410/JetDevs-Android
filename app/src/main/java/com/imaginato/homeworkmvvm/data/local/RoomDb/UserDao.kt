package com.imaginato.homeworkmvvm.data.local.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUser(demo: User)

    @Query("SELECT name FROM User_table WHERE id = :id")
     fun getUserById(id: Long): String
}