package com.illithas.tahacontactapplication.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.illithas.tahacontactapplication.data.entity.Contact


@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile private var instance: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, ContactRoomDatabase::class.java, "contacts")
                .fallbackToDestructiveMigration()
                .build()
    }

}