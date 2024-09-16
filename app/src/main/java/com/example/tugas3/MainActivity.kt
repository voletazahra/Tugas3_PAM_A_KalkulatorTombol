package com.example.tugas3



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var displayText by remember { mutableStateOf("") }
    var lastOperator by remember { mutableStateOf("") }
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }

    fun onDigitClick(digit: String) {
        displayText += digit
        if (lastOperator.isEmpty()) {
            operand1 += digit
        } else {
            operand2 += digit
        }
    }

    fun onOperatorClick(operator: String) {
        if (operand1.isNotEmpty()) {
            lastOperator = operator
            displayText += " $operator "
        }
    }

    fun calculate() {
        if (operand1.isNotEmpty() && operand2.isNotEmpty() && lastOperator.isNotEmpty()) {
            val result = when (lastOperator) {
                "+" -> operand1.toDouble() + operand2.toDouble()
                "-" -> operand1.toDouble() - operand2.toDouble()
                "*" -> operand1.toDouble() * operand2.toDouble()
                "/" -> if (operand2.toDouble() != 0.0) operand1.toDouble() / operand2.toDouble() else "Error"
                else -> ""
            }

            displayText = if (result is Double && result == result.toInt().toDouble()) {
                result.toInt().toString()
            } else {
                result.toString()
            }

            operand1 = result.toString()
            operand2 = ""
            lastOperator = ""
        }
    }


    fun clear() {
        displayText = ""
        operand1 = ""
        operand2 = ""
        lastOperator = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 32.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { button ->
                    Button(
                        onClick = {
                            when (button) {
                                "C" -> clear()
                                "=" -> calculate()
                                "+", "-", "*", "/" -> onOperatorClick(button)
                                else -> onDigitClick(button)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC0CB)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(text = button, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    MaterialTheme {
        CalculatorApp()
    }
}
