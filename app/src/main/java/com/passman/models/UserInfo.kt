package com.passman.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = UserInfo.TABLE_NAME,
    indices = [Index(value = arrayOf("_id"), unique = true)]
)
data class UserInfo(@PrimaryKey(autoGenerate = true) val _id: Int = 0,val password:String){
    companion object {
        const val TABLE_NAME = "user_info"
    }
}
