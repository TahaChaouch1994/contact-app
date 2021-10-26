package com.illithas.tahacontactapplication.ui.home.contactdetail


import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.illithas.tahacontactapplication.R
import com.illithas.tahacontactapplication.databinding.FragmentContactDetailBinding
import dagger.hilt.android.AndroidEntryPoint





@AndroidEntryPoint
class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private val viewModel: ContactDetailFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val contactId = requireArguments().getString("idcontact").toString()
        getUserFromRoom(contactId)
    }








    private fun getUserFromRoom(contactId: String) {

        viewModel.getContactById(contactId.toInt())

        viewModel.oneContact.observe(viewLifecycleOwner,{
            (activity as AppCompatActivity).supportActionBar?.title = it?.firstName
            binding.name.text = it?.firstName
            binding.number.text = it?.number
            binding.email.text = it?.email

            Glide.with(this)
                .load(it?.profilePictureUrl)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar).into(binding.image)
        })
    }

}