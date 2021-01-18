package com.example.safejoke.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.safejoke.JokeApplication
import com.example.safejoke.R
import com.example.safejoke.model.JokeNewViewModel
import com.example.safejoke.model.JokeViewModel
import com.example.safejoke.model.JokeViewModelFactory

class NewJokeActivity :AppCompatActivity() {

    private lateinit var editSetupView: EditText
    private lateinit var editPunchlineView: EditText

    private val newJokeViewModel: JokeNewViewModel by viewModels {
        JokeViewModelFactory((application as JokeApplication))
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newjoke)
        editSetupView = findViewById(R.id.editSetup)
        editPunchlineView = findViewById(R.id.editPunchline)


        val buttonSafe = findViewById<Button>(R.id.safe_new_joke_button)
        buttonSafe.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editSetupView.text) || TextUtils.isEmpty(editPunchlineView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val jokeSetup = editSetupView.text.toString()
                val jokePunchline = editPunchlineView.text.toString()
                replyIntent.putStringArrayListExtra(EXTRA_REPLY, arrayListOf(jokeSetup, jokePunchline))
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

//        val buttonGenerate = findViewById<Button>(R.id.generate_joke)
//        buttonGenerate.setOnClickListener{
//            val intent = Intent()
//            val newJoke = newJokeViewModel.generateJoke()
//        }
//
//        )
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
