package com.illithas.tahacontactapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.illithas.tahacontactapplication.data.entity.Contact

@Dao
interface ContactDao {


    @Query("SELECT * FROM contact WHERE id = :contactId")
    fun getContactById(contactId: Int): LiveData<Contact?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contacts: List<Contact>)

}