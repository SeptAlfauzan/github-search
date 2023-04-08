package com.example.githubprofile.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubprofile.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>
}