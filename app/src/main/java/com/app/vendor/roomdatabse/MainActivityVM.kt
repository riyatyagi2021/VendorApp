package com.app.vendor.roomdatabse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityVM(app:Application):AndroidViewModel(app) {

   var allUsers :MutableLiveData<List<User>?>

    init {
        allUsers= MutableLiveData()
        GlobalScope.launch {
            getAllUsers()
        }

    }
    fun getAllUsersObservers(): MutableLiveData<List<User>?> {
        return allUsers
    }


    fun getAllUsers() {
        val userDao = RoomAppDB.getAppDB((getApplication()))?.userDao()
        val list = userDao?.getUserInfo()
        GlobalScope.launch {
            allUsers.postValue(list)
        }

    }


    fun insertUserInfo(entity: User){
        val userDao = RoomAppDB.getAppDB((getApplication()))?.userDao()
        GlobalScope.launch {
            userDao?.insert(entity)
            getAllUsers()
        }

    }

    fun updateUserInfo(entity: User){
        val userDao = RoomAppDB.getAppDB(getApplication())?.userDao()
        userDao?.delete(entity.id?:0)
        getAllUsers()
    }

    fun deleteUserInfo(user: User){
        val userDao = RoomAppDB.getAppDB(getApplication())?.userDao()
        userDao?.delete(user.id?:0)


        getAllUsers()

    }

}