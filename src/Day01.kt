fun main() {
    fun parseInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach { line ->
            val split = line.split(" ")
            left.add(split[0].toInt())
            right.add(split[split.size - 1].toInt())
        }
        return Pair(left, right)
    }

    fun part1(input: List<String>): Int {
        val (left, right) = parseInput(input)

        left.sortBy { it }
        right.sortBy { it }

        var result = 0
        left.zip(right).forEach { (l, r) ->
            result += if (l > r) {
                l - r
            } else {
                r - l
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val (left, right) = parseInput(input)

        return left.sumOf { it * right.count { el -> it == el } }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
