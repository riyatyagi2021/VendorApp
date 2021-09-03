package com.app.vendor.roomdatabse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface UserDAO {

    @Insert
  fun insert(user:User)

    @Update
    fun update(user:User)

    @Query("select * from UserDetails")
    fun getUserInfo(): List<User>?

  @Query("delete from userdetails where id = :userId")
  fun delete(userId : Int)
}