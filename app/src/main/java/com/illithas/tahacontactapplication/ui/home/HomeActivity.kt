package com.illithas.tahacontactapplication.ui.home

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.illithas.tahacontactapplication.R
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_contact_detail.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                progress_bar_2.visibility = View.VISIBLE;Navigation.findNavController(
                    this,
                    R.id.nav_auth_fragment
                ).navigate(R.id.contactFragment)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}