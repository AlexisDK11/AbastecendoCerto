package com.example.abastecendocerto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abastecendocerto.databinding.ActivityListaCombustivelBinding
import com.example.abastecendocerto.databinding.ActivityMainBinding

class ListaCombustivelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListaCombustivelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaCombustivelBinding.inflate( layoutInflater )

        setContentView(binding.root)

        binding.lvCombustiveis.setOnItemClickListener { parent, view, position, id ->
            val nomeCombustível = parent.getItemAtPosition(position) as String
            intent.putExtra( "nomeCombustível", nomeCombustível )
            setResult( RESULT_OK, intent )
            finish()
        }
    }
}