package com.passman.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = CardInfo.TABLE_NAME,
    indices = [Index(value = arrayOf("_id"), unique = true)]
)
data class CardInfo(@PrimaryKey(autoGenerate = true) val _id: Int = 0,
                    val bankName: String?,
                    val cardNumber: String?,
                    val expiryDate: String?,
                    val cvvNo: String?,
                    val pin: String?,
                    val notes: String?)
{
    companion object {
        const val TABLE_NAME = "card_info"
    }
}