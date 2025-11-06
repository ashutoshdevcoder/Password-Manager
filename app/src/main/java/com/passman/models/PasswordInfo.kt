package com.passman.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = PasswordInfo.TABLE_NAME,
    indices = [Index(value = arrayOf("_id"), unique = true)]
)
data class PasswordInfo(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val account: String?,
    val username: String?,
    val loginPassword: String?,
    val profilePassword: String?,
    val website: String?,
    val notes: String?
){
    companion object {
        const val TABLE_NAME = "password_info"
    }
}
