package com.illithas.tahacontactapplication.data.repository

import androidx.lifecycle.LiveData
import com.illithas.tahacontactapplication.data.entity.Contact
import com.illithas.tahacontactapplication.data.local.ContactDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(private val contactDao: ContactDao) {


    fun getContactById(id: Int): LiveData<Contact?> = contactDao.getContactById(id)

    suspend fun insertContacts(contacts: List<Contact>) = contactDao.insertAllContacts(contacts)
}
