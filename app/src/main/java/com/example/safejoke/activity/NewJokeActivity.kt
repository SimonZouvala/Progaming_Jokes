package com.example.safejoke.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.safejoke.JokeApplication
import com.example.safejoke.R
import com.example.safejoke.databinding.ActivityMainBinding
import com.example.safejoke.databinding.ActivityNewjokeBinding
import com.example.safejoke.domain.Joke
import com.example.safejoke.model.JokeViewModel
import com.example.safejoke.model.JokeViewModelFactory
import kotlinx.coroutines.launch

class NewJokeActivity : AppCompatActivity() {

    private lateinit var editSetupView: EditText
    private lateinit var editPunchlineView: EditText
//    private val newJoke: Joke = Joke("","")

    private var joke: Joke = (Joke("", ""))

    private val newViewModel: JokeViewModel by viewModels {
        JokeViewModelFactory((application as JokeApplication).repository)
    }

    private lateinit var binding: ActivityNewjokeBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newjoke)
//        setContentView(R.layout.activity_newjoke)
        setContentView(binding.root)


//        editSetupView = findViewById(R.id.editSetup)
//        editPunchlineView = findViewById(R.id.editPunchline)

//        val buttonGenerate = findViewById<Button>(R.id.generate_joke)
        binding.joke = joke
        var newJoke = Joke("", "")
        binding.generateJoke.setOnClickListener {

            lifecycleScope.launch {
                newJoke = getJoke()
                binding.apply {
                    editSetup.setText(newJoke.setup)
                    editPunchline.setText(newJoke.punchline)
                    invalidateAll()
                }
            }

            setResult(Activity.RESULT_OK)
            Toast.makeText(
                applicationContext,
                R.string.joke_was_generated,
                Toast.LENGTH_LONG
            ).show()
        }

//        val buttonSafe = findViewById<Button>(R.id.safe_joke_button)
        binding.safeJokeButton.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.editSetup.text) || TextUtils.isEmpty(binding.editPunchline.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val jokeSetup = binding.editSetup.text.toString()
                val jokePunchline = binding.editPunchline.text.toString()
                replyIntent.putStringArrayListExtra(
                    EXTRA_REPLY,
                    arrayListOf(jokeSetup, jokePunchline)
                )
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }


    }

    private suspend fun getJoke(): Joke {
        return newViewModel.generateJoke()

    }


    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
