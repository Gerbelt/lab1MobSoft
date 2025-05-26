package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.cbrt
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerTask: Spinner
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var inputSymbol: EditText
    private lateinit var btnCalculate: Button
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerTask = findViewById(R.id.spinnerTask)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        inputSymbol = findViewById(R.id.inputSymbol)
        btnCalculate = findViewById(R.id.btnCalculate)
        output = findViewById(R.id.output)

        val options = arrayOf(
            "Выберите вариант",
            "1. День недели по числу (1-7)",
            "4. Прямоугольный равнобедренный треугольник",
            "10. Среднее значение (a или g)"
        )
        spinnerTask.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        spinnerTask.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                updateUIForSelection(pos)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnCalculate.setOnClickListener {
            val result = when (spinnerTask.selectedItemPosition) {
                1 -> {
                    val num = input1.text.toString().toIntOrNull()
                    if (num != null) dayOfWeek(num) else "Ошибка: введите число от 1 до 7"
                }
                2 -> {
                    val symbol = inputSymbol.text.toString().firstOrNull()
                    val value = input1.text.toString().toDoubleOrNull()
                    if (symbol != null && value != null) rightTriangle(symbol, value)
                    else "Ошибка: введите символ и значение"
                }
                3 -> {
                    val x = input1.text.toString().toDoubleOrNull()
                    val y = input2.text.toString().toDoubleOrNull()
                    val z = input3.text.toString().toDoubleOrNull()
                    val mode = inputSymbol.text.toString().firstOrNull()
                    if (x != null && y != null && z != null && mode != null)
                        meanOfThree(x, y, z, mode)
                    else "Ошибка: введите 3 числа и символ a/g"
                }
                else -> "Выберите задачу"
            }
            output.text = result
        }
    }

    private fun updateUIForSelection(pos: Int) {
        input1.visibility = View.VISIBLE
        input2.visibility = View.GONE
        input3.visibility = View.GONE
        inputSymbol.visibility = View.GONE

        when (pos) {
            1 -> {
                input1.hint = "Введите число от 1 до 7"
            }
            2 -> {
                input1.hint = "Введите значение"
                inputSymbol.visibility = View.VISIBLE
                inputSymbol.hint = "к, h или s"
            }
            3 -> {
                input1.hint = "Число 1"
                input2.visibility = View.VISIBLE
                input3.visibility = View.VISIBLE
                input2.hint = "Число 2"
                input3.hint = "Число 3"
                inputSymbol.visibility = View.VISIBLE
                inputSymbol.hint = "a или g"
            }
        }
    }


}
