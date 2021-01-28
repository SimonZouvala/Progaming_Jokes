package com.example.safejoke.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.safejoke.R
import com.example.safejoke.domain.Joke
import com.example.safejoke.model.JokeListAdapter.JokeViewHolder

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class JokeListAdapter : ListAdapter<Joke, JokeViewHolder>(JokesComparator()) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.create(parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.setup, current.punchline)
    }

    /**
     * ViewHolder for Joke items. All work is done by data binding.
     */
    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val jokeSetupView: TextView = itemView.findViewById(R.id.text_setup)
        private val jokePunchlineView: TextView = itemView.findViewById(R.id.text_punchline)

        fun bind(setup: String, punchline: String) {
            jokeSetupView.text = setup
            jokePunchlineView.text = punchline
        }

        companion object {
            fun create(parent: ViewGroup): JokeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return JokeViewHolder(view)
            }
        }
    }

    /**
     * Class that compare Jokes in RecyclerView
     */
    class JokesComparator : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return (oldItem.setup == newItem.setup && oldItem.punchline == newItem.punchline)
        }
    }
}