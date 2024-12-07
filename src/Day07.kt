import java.math.BigInteger

fun main() {
    val dayString = "07"

    fun buildTree(
        list: List<Int>,
        result: String,
        operator: String,
        tree: MutableList<String>,
        operations: List<String>
    ) {
        if (list.isEmpty()) {
            tree.add(result)
            return
        }
        val mutableList = list.toMutableList()
        var tmpResult = result
        val first = mutableList.removeAt(0)
        if (tmpResult.isEmpty()) {
            tmpResult = first.toString()
        } else {
            tmpResult += "$operator$first"
        }
        operations.forEach({
            buildTree(mutableList, tmpResult, it, tree, operations)
        })
    }

    fun calculate(it: String): BigInteger {
        var res = BigInteger.ZERO
        var tmpNumber = ""
        var tmpOperator = ""
        for (i in it) {
            if (i.isDigit()) {
                tmpNumber += i
            } else {
                if (res == BigInteger.ZERO) {
                    res = tmpNumber.toBigInteger()
                    tmpNumber = ""
                    tmpOperator = i.toString()
                    continue
                }
                if (tmpOperator == "+") {
                    res += tmpNumber.toBigInteger()
                }
                if (tmpOperator == "*") {
                    res *= tmpNumber.toBigInteger()
                }
                if (tmpOperator == "|") {
                    tmpOperator += i.toString()
                    continue
                }
                if (tmpOperator == "||") {
                    res = (res.toString() + tmpNumber).toBigInteger()
                }
                tmpOperator = i.toString()
                tmpNumber = ""
            }
        }
        if (tmpOperator == "+") {
            res += tmpNumber.toBigInteger()
        }
        if (tmpOperator == "*"){
            res *= tmpNumber.toBigInteger()
        }
        if (tmpOperator == "||") {
            res = (res.toString() + tmpNumber).toBigInteger()
        }
        return res
    }

    fun calculateCombinations(equation: BigInteger, values: String, operations: List<String>): Boolean {
        val numbers = values.split(" ").map { it.toInt() }
        var tree = mutableListOf<String>()
        buildTree(numbers.toMutableList(), "", "", tree, operations)
        tree.toSet().forEach {
            val res = calculate(it)
            if (res == equation) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): BigInteger {
        var result = BigInteger.ZERO
        for (line in input) {
            val split = line.split(": ")
            val equation = split[0].toBigInteger()
            val values = split[1]
            var isPossible = calculateCombinations(equation, values, listOf("+", "*"))
            if (isPossible) {
                result += equation
            }
        }
        return result
    }

    fun part2(input: List<String>): BigInteger {
        var result = BigInteger.ZERO
        for (line in input) {
            val split = line.split(": ")
            val equation = split[0].toBigInteger()
            val values = split[1]
            var isPossible = calculateCombinations(equation, values, listOf("+", "*", "||"))
            if (isPossible) {
                result += equation
            }
        }
        return result
    }

    val testInput = readInput("Day${dayString}_test")
    /*part1(testInput).println()
    check(part1(testInput) == BigInteger.valueOf(3749))*/

    val input = readInput("Day${dayString}")
    //part1(input).println()
    part2(input).println()
}