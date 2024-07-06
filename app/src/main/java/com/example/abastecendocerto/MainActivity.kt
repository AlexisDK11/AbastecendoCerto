package com.example.abastecendocerto

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abastecendocerto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate( layoutInflater )

        setContentView(binding.root)

        binding.btnLimpar.setOnClickListener{
            btnLimparOnClickListener()
        }


        binding.btnCalcularMenorPreco.setOnClickListener{
            calcularMenorPreco()
        }

        binding.btnBuscar1.setOnClickListener{
            btnBuscar1OnClickListener()
        }

        binding.btnBuscar2.setOnClickListener{
            btnBuscar2OnClickListener()
        }


    }

    private fun btnBuscar2OnClickListener() {
        val intent = Intent( this, ListaCombustivelActivity::class.java )
        getResultCombustivel2.launch( intent )
    }

    private fun btnBuscar1OnClickListener() {
        val intent = Intent( this, ListaCombustivelActivity::class.java )
        getResultCombustivel1.launch( intent )
    }

    private fun btnLimparOnClickListener() {
        binding.etCombustivel1.setText("")
        binding.etCombustivel2.setText("")
        binding.kmPorL1.setText("")
        binding.kmPorL2.setText("")
        binding.valor1.setText("")
        binding.valor2.setText("")
        binding.valor1.error = null
        binding.valor2.error = null
        binding.kmPorL1.error = null
        binding.kmPorL2.error = null
        binding.etCombustivel1.requestFocus()

    }

    private val getResultCombustivel1 = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {

        if ( it.resultCode == RESULT_OK )  {
            if ( it.data != null ) {
                val retorno = it.data?.getStringExtra("nomeCombustível")
                binding.etCombustivel1.setText( retorno.toString() )
            }
        }

    }
    private val getResultCombustivel2 = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {

        if ( it.resultCode == RESULT_OK )  {
            if ( it.data != null ) {
                val retorno = it.data?.getStringExtra("nomeCombustível")
                binding.etCombustivel2.setText( retorno.toString() )
            }
        }

    }

    private fun calcularMenorPreco() {
        val valor1 = binding.valor1.text.toString().toDoubleOrNull()
        val valor2 = binding.valor2.text.toString().toDoubleOrNull()
        val kmPorLitro1 = binding.kmPorL1.text.toString().toDoubleOrNull()
        val kmPorLitro2 = binding.kmPorL2.text.toString().toDoubleOrNull()
        val combustivel1 = binding.etCombustivel1.text.toString()
        val combustivel2 = binding.etCombustivel2.text.toString()

        if (valor1 != null && valor2 != null && kmPorLitro1 != null && kmPorLitro2 != null) {
            if (valor1 > 0 && valor2 > 0 && kmPorLitro1 > 0 && kmPorLitro2 > 0) {
                val custoPorKm1 = valor1 / kmPorLitro1
                val custoPorKm2 = valor2 / kmPorLitro2

                val combustivelMaisBarato =
                    if (custoPorKm1 < custoPorKm2) combustivel1 else combustivel2

                exibirModalComparacao(combustivelMaisBarato, custoPorKm1, custoPorKm2)
            } else {
                Toast.makeText(
                    this,
                    "Digite valores válidos para os combustíveis e quilômetros por litro.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            if (valor1 == null) {
                binding.valor1.error = "Campo obrigatório"
                binding.valor1.requestFocus()
            }
            if (valor2 == null) {
                binding.valor2.error = "Campo obrigatório"
                binding.valor2.requestFocus()
            }
            if (kmPorLitro1 == null) {
                binding.kmPorL1.error = "Campo obrigatório"
                binding.kmPorL1.requestFocus()
            }
            if (kmPorLitro2 == null) {
                binding.kmPorL2.error = "Campo obrigatório"
                binding.kmPorL2.requestFocus()
            }
        }
    }

    private fun exibirModalComparacao(
        combustivelMaisBarato: String,
        custoPorKm1: Double,
        custoPorKm2: Double
    ) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_comparacao_combustiveis, null)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Comparação de Combustíveis")

        val alertDialog = dialogBuilder.create()

        alertDialog.show()

        val barGraph = dialogView.findViewById<BarGraphView>(R.id.barGraph)
        val tvResultado = dialogView.findViewById<TextView>(R.id.tvResultadoComparacao)

        barGraph.setValues(custoPorKm1.toFloat(), custoPorKm2.toFloat(), binding.etCombustivel1.text.toString(),binding.etCombustivel2.text.toString() )

        val resultadoText = "O combustível mais barato é: $combustivelMaisBarato"

        val spannableBuilder = SpannableStringBuilder(resultadoText)
        val boldSpan = StyleSpan(Typeface.BOLD)

        val startIndex = resultadoText.indexOf(combustivelMaisBarato)

        spannableBuilder.setSpan(
            boldSpan,
            startIndex,
            startIndex + combustivelMaisBarato.length,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvResultado.text = spannableBuilder
    }


}