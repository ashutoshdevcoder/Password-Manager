package com.passman.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.passman.models.CardInfo
import com.passman.models.PasswordInfo
import com.passman.models.UserInfo

@Database(entities = [PasswordInfo::class,CardInfo::class,UserInfo::class],version = AppDatabase.Meta.DATABASE_VERSION,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserInfoDao(): UserInfoDao
    abstract fun getCardInfoDao(): CardInfoDao
    abstract fun getPasswordInfoDao(): PasswordInfoDao
    object Meta {
        const val DATABASE_NAME = "passman_database"
        const val DATABASE_VERSION = 1
    }
}