package com.passman.di

import android.content.Context
import androidx.room.Room
import com.passman.PassKeeperApplication
import com.passman.database.AppDatabase
import com.passman.database.CardInfoDao
import com.passman.database.PasswordInfoDao
import com.passman.database.UserInfoDao
import com.passman.helper.SQLCipherUtils
import com.passman.helper.getPassphrase
import com.passman.helper.logit
import com.passman.prefsHelper.EncryptedPreferenceHelperImpl
import com.passman.prefsHelper.IEncryptedPreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(): Context {
        return PassKeeperApplication.appContext
    }
    @Provides
    @Singleton
    fun provideIEncryptedPreferenceHelper(preferenceHelperImpl: EncryptedPreferenceHelperImpl): IEncryptedPreferenceHelper {
        return preferenceHelperImpl
    }

    @Provides
    @Singleton
    fun provideEncryptedPreferenceHelperImpl(context: Context): EncryptedPreferenceHelperImpl {
        return EncryptedPreferenceHelperImpl(context)
    }

    @Singleton
    @Provides
    fun getCardInfoDao(appDatabase: AppDatabase):CardInfoDao {
        return appDatabase.getCardInfoDao()
    }
    @Singleton
    @Provides
    fun getPasswordInfoDao(appDatabase: AppDatabase):PasswordInfoDao {
        return appDatabase.getPasswordInfoDao()
    }
    @Singleton
    @Provides
    fun getUserInfoDao(appDatabase: AppDatabase):UserInfoDao {
        return appDatabase.getUserInfoDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {

        /*val passphrase = context.getPassphrase()
        try {
            val dbFile = context.getDatabasePath(AppDatabase.Meta.DATABASE_NAME)
            val state = SQLCipherUtils.getDatabaseState(context, dbFile)

            if (state == SQLCipherUtils.State.UNENCRYPTED) {
                val dbTemp = context.getDatabasePath("_temp.db")

                dbTemp.delete()

                SQLCipherUtils.encryptTo(context, dbFile, dbTemp, passphrase)

                val dbBackup = context.getDatabasePath("_backup.db")

                if (dbFile.renameTo(dbBackup)) {
                    if (dbTemp.renameTo(dbFile)) {
                        dbBackup.delete()
                    } else {
                        dbBackup.renameTo(dbFile)
//                        throw IOException("Could not rename $dbTemp to $dbFile")
                    }
                } else {
                    dbTemp.delete()
//                    throw IOException("Could not rename $dbFile to $dbBackup")
                }
            }
        }
        catch (e:Exception)
        {
            logit(e.printStackTrace())
        }*/

        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.Meta.DATABASE_NAME)
            //.openHelperFactory(SupportFactory(passphrase))
            .fallbackToDestructiveMigration()
            .build()
    }
}