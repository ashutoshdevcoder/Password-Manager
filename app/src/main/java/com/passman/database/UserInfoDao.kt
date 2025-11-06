package com.passman.database

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.passman.models.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
@Keep
interface UserInfoDao {
    @Transaction
    fun insertCardInfo(items: List<UserInfo>){
        items.forEach {
            insertUserInfo(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserInfo(item: UserInfo)

    @Query("SELECT * FROM user_info")
    fun getUserInfoLiveData() : Flow<List<UserInfo>>

    @Query("SELECT EXISTS(SELECT * FROM user_info WHERE password = :password)")
    fun isUserExists(password: String): Boolean

    @Update
    fun updateUserInfo(item: UserInfo)

    @Query("DELETE FROM user_info where _id = :id")
    fun deleteUserInfo(id : Int)
}