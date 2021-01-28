package com.example.safejoke.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safejoke.JokeApplication
import com.example.safejoke.R
import com.example.safejoke.databinding.ActivityMainBinding
import com.example.safejoke.domain.Joke
import com.example.safejoke.model.JokeListAdapter
import com.example.safejoke.model.JokeViewModel
import com.example.safejoke.model.JokeViewModelFactory

/**
 * Main Activity that show all Jokes from Database.
 * Also allows insert new Joke through [NewJokeActivity]
 */
class MainActivity : AppCompatActivity() {

    private val newJokeActivityRequestCode = 1

    private val jokeViewModel: JokeViewModel by viewModels {
        JokeViewModelFactory((application as JokeApplication).repository)
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        val adapter = JokeListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        jokeViewModel.allJokes.observe(this, { jokes ->
            jokes?.let { adapter.submitList(it) }
        })

        binding.addNewJokeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NewJokeActivity::class.java)
            startActivityForResult(intent, newJokeActivityRequestCode)
        }

        binding.deleteJokesButton.setOnClickListener {
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
                val joke = Joke(newJoke[0].toString(), newJoke[1].toString())
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
