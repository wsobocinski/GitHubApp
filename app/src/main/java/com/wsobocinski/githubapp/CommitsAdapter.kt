package com.wsobocinski.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wsobocinski.githubapp.model.SingleCommit
import kotlinx.android.synthetic.main.commit_item.view.*

class CommitsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commit_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is  CommitViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    fun submitList(commits: List<SingleCommit>) {
        differ.submitList(commits)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object: DiffUtil.ItemCallback<SingleCommit>() {
        override fun areItemsTheSame(oldItem: SingleCommit, newItem: SingleCommit): Boolean {
            return oldItem.shaValue == newItem.shaValue
        }

        override fun areContentsTheSame(oldItem: SingleCommit, newItem: SingleCommit): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    class CommitViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        private val commitMessage = itemView.message
        private val shaValue = itemView.shaValue
        private val author = itemView.author

        fun bind(singleCommit: SingleCommit) {
            commitMessage.text = singleCommit.message
            shaValue.text = singleCommit.shaValue
            author.text = singleCommit.author
        }
    }

}