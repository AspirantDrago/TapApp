package com.aspirant.clicker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var score: Long = 0
    var inc: Long = 1

    private lateinit var btnTap: Button
    private lateinit var btnBoost: ImageButton
    private lateinit var textScore: TextView
    private lateinit var textInc: TextView

    private lateinit var shared: SharedPreferences
    private lateinit var sharedEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        shared = getSharedPreferences("main", 0)
        sharedEditor = shared.edit()
        load()

        btnTap = findViewById(R.id.btn_tap)
        btnBoost = findViewById(R.id.btn_boost)
        textScore = findViewById(R.id.text_score)
        textInc = findViewById(R.id.text_inc)

        btnTap.setOnClickListener { add() }
        btnBoost.setOnClickListener {
            save()
            startActivity(Intent(this, BoostsActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_login).setOnClickListener {
            save()

        }
    }

    private fun add() {
        score += inc
        textScore.text = String.format(getString(R.string.text_score_text), score)
    }

    override fun onResume() {
        super.onResume()
        load()

        textScore.text = String.format(getString(R.string.text_score_text), score)
        textInc.text = String.format(getString(R.string.text_inc_text), inc)
    }

    private fun save() {
        sharedEditor.putLong("score", score)
        sharedEditor.putLong("inc", inc)
        sharedEditor.commit()
    }

    private fun load() {
        score = shared.getLong("score", 0)
        inc = shared.getLong("inc", 1)
    }

    override fun onStop() {
        save()
        super.onStop()
    }
}