package com.example.alcoolgasolinakotlin // Ajuste para o seu pacote real

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var etAlcoholPrice: TextInputEditText
    private lateinit var etGasolinePrice: TextInputEditText
    private lateinit var etAlcoholEfficiency: TextInputEditText
    private lateinit var etGasolineEfficiency: TextInputEditText
    private lateinit var btnCalculate: MaterialButton
    private lateinit var btnClear: MaterialButton
    private lateinit var tvResult: MaterialTextView
    private lateinit var tvDetails: MaterialTextView
    private lateinit var cardResult: MaterialCardView
    private lateinit var btnToggleDetails: MaterialButton

    private var showDetails = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etAlcoholPrice = findViewById(R.id.etAlcoholPrice)
        etGasolinePrice = findViewById(R.id.etGasolinePrice)
        etAlcoholEfficiency = findViewById(R.id.etAlcoholEfficiency)
        etGasolineEfficiency = findViewById(R.id.etGasolineEfficiency)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnClear = findViewById(R.id.btnClear)
        tvResult = findViewById(R.id.tvResult)
        tvDetails = findViewById(R.id.tvDetails)
        cardResult = findViewById(R.id.cardResult)
        btnToggleDetails = findViewById(R.id.btnToggleDetails)

        cardResult.visibility = View.GONE
    }

    private fun setupListeners() {
        btnCalculate.setOnClickListener { calculate() }
        btnClear.setOnClickListener { clearFields() }
        btnToggleDetails.setOnClickListener { toggleDetails() }

        etAlcoholEfficiency.setText("7.0")
        etGasolineEfficiency.setText("10.0")
    }

    private fun calculate() {
        if (validateFields()) {
            val alcoholPrice = etAlcoholPrice.text.toString().toDouble()
            val gasolinePrice = etGasolinePrice.text.toString().toDouble()
            val alcoholEfficiency = etAlcoholEfficiency.text.toString().toDouble()
            val gasolineEfficiency = etGasolineEfficiency.text.toString().toDouble()

            val ratio = calculateRatio(alcoholPrice, gasolinePrice)
            val costPerKmAlcohol = calculateCostPerKm(alcoholPrice, alcoholEfficiency)
            val costPerKmGasoline = calculateCostPerKm(gasolinePrice, gasolineEfficiency)

            val bestFuel = calculateBestFuel(ratio)
            val resultText = "É mais vantajoso abastecer com $bestFuel"

            val detailsText = """
                Relação Álcool/Gasolina: ${DecimalFormat("#0.000").format(ratio)}
                Custo por km com Álcool: R$ ${DecimalFormat("#0.000").format(costPerKmAlcohol)}
                Custo por km com Gasolina: R$ ${DecimalFormat("#0.000").format(costPerKmGasoline)}
            """.trimIndent()

            tvResult.text = resultText
            tvResult.setTextColor(
                if (bestFuel == "ÁLCOOL") ContextCompat.getColor(this, R.color.green) // Use seu pacote correto
                else ContextCompat.getColor(this, R.color.blue) // Use seu pacote correto
            )

            tvDetails.text = detailsText
            cardResult.visibility = View.VISIBLE
            showDetails = false
            updateDetailsVisibility()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (etAlcoholPrice.text.isNullOrEmpty()) {
            etAlcoholPrice.error = "Preço inválido"
            isValid = false
        }

        if (etGasolinePrice.text.isNullOrEmpty()) {
            etGasolinePrice.error = "Preço inválido"
            isValid = false
        }

        if (etAlcoholEfficiency.text.isNullOrEmpty()) {
            etAlcoholEfficiency.error = "Eficiência inválida"
            isValid = false
        }

        if (etGasolineEfficiency.text.isNullOrEmpty()) {
            etGasolineEfficiency.error = "Eficiência inválida"
            isValid = false
        }

        return isValid
    }

    private fun clearFields() {
        etAlcoholPrice.text?.clear()
        etGasolinePrice.text?.clear()
        etAlcoholEfficiency.setText("7.0")
        etGasolineEfficiency.setText("10.0")
        cardResult.visibility = View.GONE
    }

    private fun toggleDetails() {
        showDetails = !showDetails
        updateDetailsVisibility()
    }

    private fun updateDetailsVisibility() {
        tvDetails.visibility = if (showDetails) View.VISIBLE else View.GONE
        btnToggleDetails.text = if (showDetails) "Ocultar detalhes" else "Mostrar detalhes"
    }

    companion object {
        fun calculateBestFuel(ratio: Double): String {
            return if (ratio < 0.7) "ÁLCOOL" else "GASOLINA"
        }

        fun calculateCostPerKm(price: Double, efficiency: Double): Double {
            return if (efficiency > 0) price / efficiency else 0.0
        }

        fun calculateRatio(alcoholPrice: Double, gasolinePrice: Double): Double {
            return if (gasolinePrice > 0) alcoholPrice / gasolinePrice else 0.0
        }
    }
}
