package com.illithas.tahacontactapplication.ui.home.contactdetail

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.illithas.tahacontactapplication.data.entity.Contact
import com.illithas.tahacontactapplication.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ContactDetailFragmentViewModel @Inject constructor(
    private val context: Context,
    private val contactRepository: ContactRepository,
) : ViewModel() {

    lateinit var oneContact : LiveData<Contact?>



     fun getContactById(contactId: Int){
        viewModelScope.launch {
            oneContact = contactRepository.getContactById(contactId)
        }
    }

}
