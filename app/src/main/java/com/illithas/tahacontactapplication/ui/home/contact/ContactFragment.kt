package com.illithas.tahacontactapplication.ui.home.contact

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.illithas.tahacontactapplication.R
import com.illithas.tahacontactapplication.data.entity.Contact
import com.illithas.tahacontactapplication.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import java.util.ArrayList


@AndroidEntryPoint
class ContactFragment : Fragment(), ContactAdapter.RecommandItemListener {
    private lateinit var binding: FragmentContactBinding
    private val viewModel: ContactFragmentViewModel by viewModels()
    private var arrayList: ArrayList<Contact> = ArrayList<Contact>()
    private var listaContact: ArrayList<Contact> = ArrayList<Contact>()
    private lateinit var adapterContact: ContactAdapter


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

                //Permission is Granted
                (activity as AppCompatActivity).supportActionBar?.show()
                binding.progressBar.visibility = View.GONE
                binding.permissionBox.visibility = View.GONE
                fetchContactList()

            } else {
                //Permission is Denied
                (activity as AppCompatActivity).supportActionBar?.hide()
                binding.progressBar.visibility = View.GONE
                binding.permissionBox.visibility = View.VISIBLE

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)


        binding.progressBar.visibility = View.VISIBLE

        // to tell the host activity that your fragment has menu options that it wants to add
        setHasOptionsMenu(true)
        requestPermission()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "My Contacts"




        binding.permissionButton.setOnClickListener {


            requestPermissionLauncher.launch(
                Manifest.permission.READ_CONTACTS
            )

            binding.progressBar.visibility = View.VISIBLE


        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                listaContact = arrayList.filter {
                    it.firstName?.uppercase()?.contains(
                        searchView.query.toString().uppercase().trim()
                    )!! || it.number?.uppercase()
                        ?.contains(searchView.query.toString().uppercase().trim())!!
                } as ArrayList<Contact>
                adapterContact.setItems(listaContact)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

        })
    }


    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is Granted
                (activity as AppCompatActivity).supportActionBar?.show()
                binding.progressBar.visibility = View.GONE
                binding.permissionBox.visibility = View.GONE
                fetchContactList()
            }


            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_CONTACTS
            ) -> {
                // Permission is Denied
                (activity as AppCompatActivity).supportActionBar?.hide()
                binding.permissionBox.visibility = View.VISIBLE

                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }


        }
    }


    private fun fetchContactList() {
        viewModel.getContactDetails()
        arrayList = viewModel.contact.value!!
        setupRecyclerViewAcheter()
        viewModel.saveContacts(arrayList)
        Log.i("FinalArray", "${viewModel.contact.value}")
    }


    private fun setupRecyclerViewAcheter() {
        adapterContact = ContactAdapter(this)
        binding.contactRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.contactRecycle.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.contactRecycle.adapter = adapterContact
        adapterContact.setItems(arrayList)
    }

    override fun onClickedContact(contactId: Int) {

        val bundle = bundleOf("idcontact" to contactId.toString())
        findNavController().navigate(R.id.action_contactFragment_to_contactDetailFragment, bundle)
    }




}
