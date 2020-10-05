package com.wsobocinski.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsobocinski.githubapp.model.SingleCommit
import kotlinx.android.synthetic.main.commit_item.view.*

class CommitsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<SingleCommit> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commit_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is  CommitViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(commits: List<SingleCommit>) {
        items = commits
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

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