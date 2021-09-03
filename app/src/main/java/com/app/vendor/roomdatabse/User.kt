package com.app.vendor.roomdatabse

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserDetails")
data class User(val firstName: String,
                val lastName: String,
                @PrimaryKey(autoGenerate = true) val id: Int? = null)