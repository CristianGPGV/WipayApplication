package com.prueba.wipayapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prueba.wipayapplication.databinding.ActivityMainBinding
import com.prueba.wipayapplication.modules.characters.CharactersActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        binding.startButton.setOnClickListener {
            goToCharactersActivity()
        }
    }

    private fun goToCharactersActivity() {
        val intent = Intent(this, CharactersActivity::class.java)
        startActivity(intent)
    }

}