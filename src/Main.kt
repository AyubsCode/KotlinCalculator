
/**
 * Project Description: Build a console calculator app that performs basic arithmetic operations on integers and decimals.
 *
 * Date: August 6th, 2025
 *
 * Features:
 *1. Addition,Subtraction,Multiplication, Division
 *2. Accept any range of numbers/operators from user in a single line
 *3. Display the result with appropriate formatting
 *
 * Project Status: Complete
 */

fun tokenize(expr: String): MutableList<String>{
    val token = mutableListOf<String>()
    var current =""

    for (char in expr){
        when {
            char.isDigit() || char == '.' -> current += char
            char in listOf('+','-','*','/') -> {
                if (current.isNotEmpty()) token.add(current)
                token.add(char.toString())
                current =""
            }
            char == ' ' -> continue //ignore spaces
            else -> throw IllegalArgumentException("Invalid character: $char")
        }
    }
    if (current.isNotEmpty()) token.add(current)
    return token
}

fun evaluate(expr: MutableList<String>): Double {
    // Handle *, /
    var index = 1
    while (index < expr.size - 1) {
        val op = expr[index]
        if (op == "*" || op == "/") {
            val left = expr[index - 1].toDoubleOrNull()
            val right = expr[index + 1].toDoubleOrNull()
            if (left == null || right == null) throw IllegalArgumentException("Invalid number")

            val result = if (op == "*") left * right else left / right

            expr[index - 1] = result.toString()
            expr.removeAt(index + 1)
            expr.removeAt(index)
            index = 1
        } else {
            index += 2
        }
    }

    // Handle +, -
    index = 1
    while (index < expr.size - 1) {
        val op = expr[index]
        if (op == "+" || op == "-") {
            val left = expr[index - 1].toDoubleOrNull()
            val right = expr[index + 1].toDoubleOrNull()
            if (left == null || right == null) throw IllegalArgumentException("Invalid number")

            val result = if (op == "+") left + right else left - right

            expr[index - 1] = result.toString()
            expr.removeAt(index + 1)
            expr.removeAt(index)
            index = 1
        }
        else {
            index += 2
        }
    }
    return expr.first().toDoubleOrNull() ?: throw IllegalArgumentException("Could not evaluate.")
}
fun main() {
    //get user input
    print("Provide an expression to evaluate(e.g. 1+1+2): ")
    val input = readln().trim()

    try{
        val token = tokenize(input)
        val result = evaluate(token)

        val display = if (result %1==0.0) result.toInt() else result
        println("Result: $display")
    }
    catch (e: Exception){
        println("[!] Error: ${e.message}")
    }
}