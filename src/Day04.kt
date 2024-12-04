fun main() {
    val dayString = "04"
    val xmas = "XMAS"
    val mas = "MAS"

    fun countVertical(s: String): Int {
        var buffer = ""
        var count = 0
        // count vertical
        for (c in s) {
            if (buffer.length < 4) {
                buffer += c
            }
            if (buffer.length == 4) {
                if (buffer == xmas ||
                    buffer.reversed() == xmas.reversed() ||
                    buffer == xmas.reversed() ||
                    buffer.reversed() == xmas
                ) {
                    count++
                }
                buffer = buffer.drop(1)
            }
        }
        return count
    }

    fun countHorizontal(s: List<String>): Int {
        var count = 0
        for (x in 0 until s[0].length) {
            var buffer = ""
            for (y in 0 until s.size) {
                buffer += s[y][x]
            }
            count += countVertical(buffer)
        }
        return count
    }

    fun iterateDiagonally(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].length
        var count = 0
        // Number of diagonals is rows + cols - 1
        for (diag in 0 until rows + cols - 1) {
            val diagonal = StringBuilder()

            for (row in 0 until rows) {
                val col = diag - row
                if (col in 0 until cols) {
                    diagonal.append(input[row][col])
                }
            }
            count += countVertical(diagonal.toString())
        }

        return count
    }
    fun rotate90Clockwise(strings: List<String>): List<String> {
        val rows = strings.size
        val cols = strings[0].length
        val rotated = MutableList(cols) { StringBuilder() }

        for (col in 0 until cols) {
            for (row in rows - 1 downTo 0) {
                rotated[col].append(strings[row][col])
            }
        }

        return rotated.map { it.toString() }
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach {
            count += countVertical(it)
        }
        count += countHorizontal(input)
        count += iterateDiagonally(input)
        count += iterateDiagonally(rotate90Clockwise(input))
        return count
    }

    fun checkOnMas(secondMas: String): Boolean {
        return secondMas == mas ||
                secondMas.reversed() == mas.reversed() ||
                secondMas == mas.reversed() ||
                secondMas.reversed() == mas
    }

    fun checkGrid(input: List<String>, x: Int, y: Int): Int {
        val grid = listOf(
            input[x - 1][y - 1],
            input[x - 1][y + 1],
            input[x + 1][y - 1],
            input[x + 1][y + 1]
        )
        var firstMas  = "${grid[0]}A${grid[3]}"
        var secondMas = "${grid[1]}A${grid[2]}"

        if (checkOnMas(firstMas) && checkOnMas(secondMas)) {
            return 1
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (x in 1 until input.size - 1) {
            for (y in 1 until input[0].length - 1) {
                if (input[x][y] == 'A') {
                  count += checkGrid(input, x , y)
                }
            }
        }
        return count
    }


    val input = readInput("Day${dayString}")
    part1(input).println()
    part2(input).println()
}