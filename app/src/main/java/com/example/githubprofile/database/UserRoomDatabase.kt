package com.example.githubprofile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubprofile.model.User

@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCES: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if(INSTANCES == null){
                synchronized(UserRoomDatabase::class.java){
                    INSTANCES = Room.databaseBuilder(context.applicationContext, UserRoomDatabase::class.java, "user_database").build()
                }
            }

            return INSTANCES as UserRoomDatabase
        }
    }
}