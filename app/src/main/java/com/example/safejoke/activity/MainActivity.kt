package com.example.safejoke.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safejoke.JokeApplication
import com.example.safejoke.R
import com.example.safejoke.databinding.ActivityMainBinding
import com.example.safejoke.domain.Joke
import com.example.safejoke.model.JokeListAdapter
import com.example.safejoke.model.JokeViewModel
import com.example.safejoke.model.JokeViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val newJokeActivityRequestCode = 1

    private val jokeViewModel: JokeViewModel by viewModels {
        JokeViewModelFactory((application as JokeApplication).repository)
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
//        binding.lifecycleOwner = this


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = JokeListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        jokeViewModel.allJokes.observe(this, Observer { jokes ->

            jokes?.let { adapter.submitList(it) }
        })

        val newJokeButton = findViewById<Button>(R.id.add_new_joke_button)
        newJokeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NewJokeActivity::class.java)
            startActivityForResult(intent, newJokeActivityRequestCode)
        }

        val deleteButton = findViewById<Button>(R.id.clear_jokes)
        deleteButton.setOnClickListener{
            clearJokes()
        }


    }

    private fun clearJokes() {
        jokeViewModel.delete()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newJokeActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayListExtra(NewJokeActivity.EXTRA_REPLY)?.let { newJoke ->
                val joke = Joke(newJoke.get(0).toString(), newJoke.get(1).toString())
//                Toast.makeText(
//                    applicationContext,
//                    joke.setup +":"+ joke.punchline,
//                    Toast.LENGTH_LONG
//                ).show()
                jokeViewModel.insert(joke)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
