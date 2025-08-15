package com.mason.test.data // Or your appropriate adapter package

// Import the correct binding class based on your layout file name "list_item_user.xml"
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mason.test.databinding.ListItemUserBinding
import java.util.Objects

class UserAdapter(private val onItemClicked: (User) -> Unit) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false) // <--- USE ListItemUserBinding
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.bind(currentUser)
        holder.itemView.setOnClickListener {
            onItemClicked(currentUser)
        }
    }

    // Inner class ViewHolder now uses ListItemUserBinding
    inner class UserViewHolder(private val binding: ListItemUserBinding) : // <--- USE ListItemUserBinding
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userNameItemTextView.text = user.name
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id // Assuming 'id' is the unique identifier
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return Objects.equals(oldItem, newItem)
        }
    }
}
