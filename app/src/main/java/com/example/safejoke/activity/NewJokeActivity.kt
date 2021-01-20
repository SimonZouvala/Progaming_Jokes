package com.example.safejoke.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.safejoke.JokeApplication
import com.example.safejoke.R
import com.example.safejoke.databinding.ActivityMainBinding
import com.example.safejoke.domain.Joke
import com.example.safejoke.model.JokeViewModel
import com.example.safejoke.model.JokeViewModelFactory
import kotlinx.coroutines.launch

class NewJokeActivity : AppCompatActivity() {

    private lateinit var editSetupView: EditText
    private lateinit var editPunchlineView: EditText
    private var newJoke: Joke = Joke("","")

    private val newViewModel: JokeViewModel by viewModels {
        JokeViewModelFactory((application as JokeApplication).repository)
    }

    private lateinit var binding: ActivityMainBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newjoke)
        setContentView(R.layout.activity_newjoke)

        binding.lifecycleOwner = this

        editSetupView = findViewById(R.id.editSetup)
        editPunchlineView = findViewById(R.id.editPunchline)

        val buttonGenerate = findViewById<Button>(R.id.generate_joke)
        buttonGenerate.setOnClickListener {

            lifecycleScope.launch {
                newJoke = getJoke()
            }
            binding.apply{
                editSetupView.setText(newJoke.setup)
                editPunchlineView.setText(newJoke.punchline)
            }
            setResult(Activity.RESULT_OK)
        }

        val buttonSafe = findViewById<Button>(R.id.safe_joke_button)
        buttonSafe.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editSetupView.text) || TextUtils.isEmpty(editPunchlineView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val jokeSetup = editSetupView.text.toString()
                val jokePunchline = editPunchlineView.text.toString()
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
