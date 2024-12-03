fun main() {
    val dayString = "03"

    fun part1(input: List<String>): Int {
        val regex = Regex("""mul\(\d+,\d+\)""")
        var sum = 0
        // Find all matches
        input.forEach { s ->
            regex.findAll(s).map { it.value }.toList()
                .forEach { it ->
                    it.replace("mul(", "").replace(")", "").split(",")
                        .map { it.toInt() }.let {
                            sum += it[0] * it[1]
                        }
                }
        }


        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""")
        var sum = 0
        var mulEnabled = true
        // Find all matches
        input.forEach { s ->
            regex.findAll(s).map { it.value }.toList()
                .forEach { it ->
                    if (it == "do()") {
                        mulEnabled = true
                    } else if (it == "don't()") {
                        mulEnabled = false
                    } else {
                        if (mulEnabled) {
                            it.replace("mul(", "").replace(")", "").split(",")
                                .map { it.toInt() }.let {
                                    sum += it[0] * it[1]
                                }
                        }
                    }

                }
        }


        return sum
    }

//    val testInput = readInput("Day${dayString}_test")
//    val partTest = part2(testInput)
//    check(partTest == 48)

    val input = readInput("Day${dayString}")
    part1(input).println()
    part2(input).println()
}