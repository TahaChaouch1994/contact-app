package com.illithas.tahacontactapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey
    val id: Int?,
    var firstName: String?,
    var number: String?,
    var email: String?,
    var profilePictureUrl: String?,

)