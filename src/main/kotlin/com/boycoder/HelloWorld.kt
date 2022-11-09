package com.boycoder

import jdk.nashorn.internal.codegen.OptimisticTypesPersistence
import kotlin.system.exitProcess


val help = """
    --------------------------------------
    使用说明：
1. 输入 1 + 1，按回车，即可使用计算器；
2. 注意：数字与符号之间要有空格；
3. 想要退出程序，
请输入：exit--------------------------------------""".trimIndent()
fun main() {
    val calculator = CalculatorV2()
    calculator.start()
}

class CalculatorV2 {
    fun start() {
        while(true) {
            println(help)
            val input = readLine() ?: continue
            val result = calculate(input)
            if (result == null) {
                println("输入格式不对")
                continue
            } else {
                println("$input = $result")
            }

        }
    }
    fun calculate(input: String): String? {
        if (shouldExit(input)) exitProcess(0)
        val exp = parseExpression(input) ?: return null
        val left = exp.left
        val operator = exp.operator
        val right = exp.right

        return when (operator) {
            Operation.ADD -> addString(left, right)
            Operation.MINUS -> minusString(left, right)
            Operation.MULTI -> multiString(left, right)
            Operation.DIVI -> diviString(left,right)
        }
    }
}

data class Expression(
    val left: String,
    val operator: Operation,
    val right: String
)
private fun calculate(inputList: List<String>): Int? {
    if (inputList.size != 3) return null

    val left = inputList.get(0).toInt()
    val operation = Operation.valueOf(inputList[1])
    val right = inputList.get(2).toInt()

    return when(operation) {
        Operation.ADD -> left + right
        Operation.MINUS -> left - right
        Operation.MULTI -> left * right
        Operation.DIVI -> left/right
    }

}

enum class Operation(val value: String) {
    ADD("+"),
    MINUS("-"),
    MULTI("*"),
    DIVI("/")
}

fun addString(left: String, right: String): String {
    val result = left.toInt() + right.toInt()
    return result.toString()
}

fun minusString(left: String, right: String ): String {
    val result = left.toInt() - right.toInt()
    return result.toString()
}

fun multiString(left: String, right: String ): String {
    val result = left.toInt() * right.toInt()
    return result.toString()
}



fun diviString(left: String, right: String): String {
    val result = left.toInt() / right.toInt()
    return result.toString()
}

fun shouldExit(input: String): Boolean {
    return input == "EXIT"
}

fun parseExpression(input: String): Expression? {
    val operation = parseOperator(input) ?: return null
    val list = input.split(operation.value)
    if (list.size != 2) return null

    return Expression(
        // 算式左边
        left = list[0].trim(),
        operator = operation,
        right = list[1].trim()
    )
}

// 啰嗦的写法
//fun parseOperatior(input: String): Operation? {
//    return when {
//        input.contains(Operation.ADD.value) -> Operation.ADD
//        input.contains(Operation.MINUS.value) -> Operation.MINUS
//        input.contains(Operation.MULTI.value) -> Operation.MULTI
//        input.contains(Operation.DIVI.value) -> Operation.DIVI
//        else -> null
//    }
//}

fun parseOperator(input: String): Operation? {
    Operation.values().forEach {
        if (input.contains(it.value)) {
            return it
        }
    }
    return null
}
