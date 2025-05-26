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

    // Вариант 1
    private fun dayOfWeek(n: Int): String = when (n) {
        1 -> "Понедельник"
        2 -> "Вторник"
        3 -> "Среда"
        4 -> "Четверг"
        5 -> "Пятница"
        6 -> "Суббота"
        7 -> "Воскресенье"
        else -> "Ошибка: число от 1 до 7"
    }

    // Вариант 4
    private fun rightTriangle(code: Char, value: Double): String = when (code.lowercaseChar()) {
        'к' -> {
            val h = value * sqrt(2.0)
            val s = value * value / 2
            "k=$value ⇒ h=%.2f, S=%.2f".format(h, s)
        }
        'h' -> {
            val k = value / sqrt(2.0)
            val s = k * k / 2
            "h=$value ⇒ k=%.2f, S=%.2f".format(k, s)
        }
        's' -> {
            val k = sqrt(2 * value)
            val h = k * sqrt(2.0)
            "S=$value ⇒ k=%.2f, h=%.2f".format(k, h)
        }
        else -> "Ошибка: неизвестный символ"
    }

    // Вариант 10
    private fun meanOfThree(x: Double, y: Double, z: Double, mode: Char): String = when (mode.lowercaseChar()) {
        'a' -> "Среднее арифметическое: %.2f".format((x + y + z) / 3)
        'g' -> if (x > 0 && y > 0 && z > 0)
            "Среднее геометрическое: %.2f".format(cbrt(x * y * z))
        else "Ошибка: все числа должны быть > 0"
        else -> "Ошибка: неизвестный режим (a/g)"
    }
}
//Добавлена логика вариантов еще в прошлом коммите