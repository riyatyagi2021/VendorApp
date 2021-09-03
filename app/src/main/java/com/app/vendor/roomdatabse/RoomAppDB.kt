package com.app.vendor.roomdatabse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class],version = 1)
  abstract class RoomAppDB:RoomDatabase() {

  abstract fun userDao(): UserDAO

  companion object {
    private var instance: RoomAppDB? = null

    fun getAppDB(context: Context): RoomAppDB? {
      if (instance == null) {
        instance = Room.databaseBuilder<RoomAppDB>(
          context.applicationContext, RoomAppDB::class.java,
          "App DB"
        )
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
          .build()
      }
      return instance!!
    }


  }
}