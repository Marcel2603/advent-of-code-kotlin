import kotlin.math.abs

fun main() {
    val dayString = "02"

    fun part1(input: List<String>): Int {
        val regex = "\\D+".toRegex()
        var correctCases = 0
        for (l in input) {
            val split = l.split(regex)
            val isSortedAsc = split.asSequence().zipWithNext { a, b -> a.toInt() < b.toInt() }.all { it }
            val isSortedDesc = split.asSequence().zipWithNext { a, b -> a.toInt() > b.toInt() }.all { it }
            if (!isSortedAsc && !isSortedDesc) {
                continue
            }
            for (i in split.indices) {
                if (i == split.size - 1) {
                    correctCases++
                    break
                }
                if (abs(split[i].toInt() - split[i + 1].toInt()) > 3) {
                    break
                }
            }
        }
        return correctCases
    }
    fun isSafe(report: List<Int>): Boolean {
        if (report.size < 2) return true
        val diffs = report.zipWithNext { a, b -> b - a }
        val allIncreasing = diffs.all { it in 1..3 }
        val allDecreasing = diffs.all { it in -3..-1 }
        return allIncreasing || allDecreasing
    }

    fun isSafeWithDampener(report: List<Int>): Boolean {
        if (isSafe(report)) return true
        for (i in report.indices) {
            val modifiedReport = report.filterIndexed { index, _ -> index != i }
            if (isSafe(modifiedReport)) return true
        }
        return false
    }


    fun part2(input: List<String>): Int {
        val regex = "\\D+".toRegex()

        return input.map { it.split(regex).map( String::toInt ) }
            .count { isSafeWithDampener(it) }
    }

    val testInput = readInput("Day${dayString}_test")
     check(part1(testInput) == 2)

    val input = readInput("Day${dayString}")
    part1(input).println()
    part2(input).println()
}