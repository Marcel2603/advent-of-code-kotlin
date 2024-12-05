import kotlin.math.floor

fun main() {
    val dayString = "05"

    fun checkLine(prints: String, rules: MutableMap<String, List<String>>): Boolean {
        val split = prints.split(",")
        for (j in 0 until split.size) {
            var element = split.get(j)
            val rule = rules.get(element)
            if (j == split.size - 1) {
                return true
            }
            if (rule == null) {
                return false
            }

            var nextElement = split.get(j + 1)
            if (!rule.contains(nextElement)) {
                return false
            }

        }
        return false
    }

    fun part1(input: List<String>): Int {
        var rules = mutableMapOf<String, List<String>>()
        val prints = mutableListOf<String>()
        for (line in input) {
            if (line.contains("|")) {
                val split = line.split("|")
                val get = rules.get(split[0])
                if (get == null) {
                    rules.put(split[0], mutableListOf(split[1]))
                } else {
                    val toMutableList = get.toMutableList()
                    toMutableList.add(split[1])
                    rules.put(split[0], toMutableList)
                }
                continue
            }
            if (line.isBlank()) {
                continue
            }
            prints.add(line)
        }
        var result = 0
        for (i in 0 until prints.size) {
            val line = prints.get(i)
            val checkLine = checkLine(line, rules)
            if (checkLine) {
                val split = line.split(",")
                val size = split.size
                val half = floor(size / 2.0).toInt()
                result += split[half].toInt()
            }
            checkLine.println()
        }
        return result
    }


    fun topoSort(rules: Map<String, List<String>>, line: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        val visited = mutableSetOf<Int>()
        val visiting = mutableSetOf<Int>()

        fun dfs(node: Int) {
            if (node in visiting) {
                throw IllegalArgumentException("Cycle detected in dependencies")
            }
            if (node in line && node !in visited) {
                visiting.add(node)
                rules[node.toString()]?.forEach { follower ->
                    if (follower.toInt() in line) {
                        dfs(follower.toInt())
                    }
                }
                visiting.remove(node)
                visited.add(node)
                result.add(node)
            }
        }
        line.forEach { if (it !in visited) dfs(it) }
        return result.reversed()
    }

    fun part2(input: List<String>): Int {
        var rules = mutableMapOf<String, List<String>>()
        val prints = mutableListOf<String>()
        for (line in input) {
            if (line.contains("|")) {
                val split = line.split("|")
                val get = rules.get(split[0])
                if (get == null) {
                    rules.put(split[0], mutableListOf(split[1]))
                } else {
                    val toMutableList = get.toMutableList()
                    toMutableList.add(split[1])
                    rules.put(split[0], toMutableList)
                }
                continue
            }
            if (line.isBlank()) {
                continue
            }
            prints.add(line)

        }
        var result = 0
        for (i in 0 until prints.size) {
            val line = prints.get(i)
            val checkLine = checkLine(line, rules)
            if (!checkLine) {
                val falseLine = line.split(",").map { it.toInt() }.toList()
                val sort = topoSort(rules, falseLine)
                val half = floor(sort.size / 2.0).toInt()
                result += sort[half]

            }
        }
        return result
    }

    val testInput = readInput("Day${dayString}_test")
    check(part2(testInput) == 123)

    val input = readInput("Day${dayString}")
   // part1(input).println()
    part2(input).println()
}