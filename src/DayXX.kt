fun main() {
    val dayString = "XX"

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day${dayString}_test")
    check(part1(testInput) == 1)

    val input = readInput("Day${dayString}")
    part1(input).println()
    part2(input).println()
}