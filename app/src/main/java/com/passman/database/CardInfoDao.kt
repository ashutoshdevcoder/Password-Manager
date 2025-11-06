package com.passman.database

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.passman.models.CardInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
@Keep
interface CardInfoDao {
    @Transaction
    fun insertCardInfo(items: List<CardInfo>){
        items.forEach {
            insertCardInfo(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCardInfo(item: CardInfo)

    @Query("SELECT * FROM card_info")
    fun getCardInfoLiveData() : Flow<List<CardInfo>>

    @Query("SELECT * FROM card_info where _id = :id")
    fun getCardInfo(id : Int) : Flow<CardInfo>

    @Update
    fun updateCardInfo(item: CardInfo)

    @Query("DELETE FROM card_info where _id = :id")
    fun deleteCardInfo(id : Int)

}