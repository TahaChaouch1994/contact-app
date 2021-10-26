package com.illithas.tahacontactapplication.ui.home.contact

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.illithas.tahacontactapplication.data.entity.Contact
import com.illithas.tahacontactapplication.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ContactFragmentViewModel @Inject constructor(
    private val context: Context,
    private val contactRepository: ContactRepository,
) : ViewModel() {


    var contact: MutableLiveData<ArrayList<Contact>> =
        MutableLiveData<ArrayList<Contact>>()
    private val arrayList: ArrayList<Contact> = ArrayList<Contact>()
    private val contentResolver: ContentResolver =
        context.contentResolver



    fun saveContacts(contact: List<Contact>){
        insertContacts(contact)
    }

    private fun insertContacts(contacts: List<Contact>){
        viewModelScope.launch {
            contactRepository.insertContacts(contacts)
        }
    }




    fun getContactDetails() {
        val map: HashMap<String?, Contact> = HashMap<String?, Contact>()
        val cursor: Cursor? =
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // get the contact's information
                val id = cursor
                    .getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor
                    .getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhone = cursor
                    .getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                var image: String? = null
                image = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO_THUMBNAIL_URI)
                )

                // get the user's email address
                var email: String? = null
                val cursorEmail: Cursor? = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arrayOf(id),
                    null
                )
                if (cursorEmail != null && cursorEmail.moveToFirst()) {
                    email =
                        cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                    cursorEmail.close()
                }

                // get the user's phone number
                var phone: String? = null
                if (hasPhone > 0) {
                    val cursorPhone: Cursor? = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (cursorPhone != null && cursorPhone.moveToFirst()) {
                        phone =
                            cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        cursorPhone.close()
                    }
                }

                // if the user has an email or phone then add it to contacts
                if ((!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            && !email.equals(name, ignoreCase = true)) || !TextUtils.isEmpty(phone)
                ) {
                    val contactModel = Contact(id.toInt(), name, phone, email, image)
                    if (!map.containsKey(phone)) map[phone] = contactModel
                }
            } while (cursor.moveToNext())
            cursor.close()
            finalArrayList(map)
        }
    }


    private fun finalArrayList(hashmap: HashMap<String?, Contact>) {
        arrayList.clear()
        for ((_, value) in hashmap) arrayList.add(
            value
        )
        contact.value = arrayList
    }


}