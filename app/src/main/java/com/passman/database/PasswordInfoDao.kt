package com.passman.database

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.passman.models.PasswordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
@Keep
interface PasswordInfoDao {
    @Transaction
    fun insertPasswordData(items: List<PasswordInfo>){
        items.forEach {
            insertPasswordData(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPasswordData(item: PasswordInfo)

    @Query("SELECT * FROM password_info")
    fun getPasswordLiveData() : Flow<List<PasswordInfo>>

    @Query("SELECT * FROM password_info where _id = :id")
    fun getPassword(id : Int) : Flow<PasswordInfo>

    @Update
    fun updatePasswordData(item: PasswordInfo)

    @Query("DELETE FROM password_info where _id = :id")
    fun deletePasswordData(id : Int)
}