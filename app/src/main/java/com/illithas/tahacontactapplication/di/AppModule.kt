package com.illithas.tahacontactapplication.di


import android.app.Application
import android.content.Context
import com.illithas.tahacontactapplication.data.local.ContactRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = ContactRoomDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideContactDao(db: ContactRoomDatabase) = db.contactDao()








}