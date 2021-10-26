package com.illithas.tahacontactapplication.ui.home.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.illithas.tahacontactapplication.R
import com.illithas.tahacontactapplication.data.entity.Contact
import com.illithas.tahacontactapplication.databinding.ContactItemBinding


class ContactAdapter(private val listener: ContactFragment) : RecyclerView.Adapter<RecommandViewHolder>() {


    interface RecommandItemListener {
        fun onClickedContact(contactId: Int)
    }

    private val items = ArrayList<Contact>()


    fun setItems(items: ArrayList<Contact>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommandViewHolder {
        val binding: ContactItemBinding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommandViewHolder(binding, listener as RecommandItemListener)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecommandViewHolder, position: Int) = holder.bind(items[position])
}

class RecommandViewHolder(private val itemBinding: ContactItemBinding, private val listener: ContactAdapter.RecommandItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var contact: Contact



    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind( item: Contact) {
        this.contact = item

        itemBinding.textView1.text = item.firstName
        itemBinding.textView2.text = item.number

        Glide.with(itemBinding.root)
            .load(item.profilePictureUrl)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.ic_contact).into(itemBinding.imageView)

    }


    override fun onClick(v: View?) {
        listener.onClickedContact(contact.id!!)
    }




}