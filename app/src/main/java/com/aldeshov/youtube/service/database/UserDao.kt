package com.aldeshov.youtube.service.database

import androidx.room.*
import com.aldeshov.youtube.service.database.models.LocalUser

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = 1")
    fun getUser(): LocalUser?

    @Insert
    fun insert(vararg localUser: LocalUser)

    @Delete
    fun delete(localUser: LocalUser)

    @Update
    fun update(localUser: LocalUser)
}