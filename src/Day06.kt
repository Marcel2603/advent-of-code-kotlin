fun main() {

    fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this)
            .deleteCharAt(index)
            .apply { insert(index, char) }.toString()

    class Guard(var x: Int, var y: Int, var direction: Char) {
        val up = '^'
        val down = 'V'
        val left = '<'
        val right = '>'
        val obstacle = '#'
        val walked = 'X'

        fun move(grid: MutableList<String>): Boolean {
            direction = grid[y][x]
            when (direction) {
                up -> {
                    var nextX = x
                    var nextY = y - 1
                    if (nextY < 0) {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(up, walked)
                        grid[y] = currentLine
                        return false
                    }
                    val nextChar = grid[nextY][nextX]
                    if (nextChar == obstacle) {
                        var list = grid[y].replace(up, right)
                        grid[y] = list
                        return false
                    } else {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(up, walked)
                        grid[y] = currentLine
                        var nextLine = grid[nextY]
                        nextLine = nextLine.addCharAtIndex(up, x)
                        grid[nextY] = nextLine
                        y--
                        return true
                    }
                }

                down -> {
                    val nextX = x
                    val nextY = y + 1
                    if (nextY == grid.size) {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(down, walked)
                        grid[y] = currentLine
                        return false
                    }
                    val nextChar = grid[nextY][nextX]
                    if (nextChar == obstacle) {
                        var list = grid[y].replace(down, left)
                        grid[y] = list
                        return false
                    } else {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(down, walked)
                        grid[y] = currentLine
                        var nextLine = grid[nextY]
                        nextLine = nextLine.addCharAtIndex(down, x)
                        grid[nextY] = nextLine
                        y++
                        return true
                    }
                }

                left -> {
                    val nextX = x - 1
                    val nextY = y
                    if (nextX < 0) {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(left, walked)
                        grid[y] = currentLine
                        return false
                    }
                    val nextChar = grid[nextY][nextX]
                    if (nextChar == obstacle) {
                        val list = grid[y].replace(left, up)
                        grid[y] = list
                        return false
                    } else {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(left, walked)
                        currentLine = currentLine.addCharAtIndex(left, nextX)
                        grid[y] = currentLine
                        x--
                        return true
                    }
                }

                right -> {
                    val nextX = x + 1
                    val nextY = y
                    if (nextX == grid[0].length) {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(right, walked)
                        grid[y] = currentLine
                        return false
                    }
                    val nextChar = grid[nextY][nextX]
                    if (nextChar == obstacle) {
                        val list = grid[y].replace(right, down)
                        grid[y] = list
                        return false
                    } else {
                        var currentLine = grid[y]
                        currentLine = currentLine.replace(right, walked)
                        currentLine = currentLine.addCharAtIndex(right, nextX)
                        grid[y] = currentLine
                        x++
                        return true
                    }
                }
            }
            return false
        }
    }


    val dayString = "06"

    fun part1(input: MutableList<String>): Triple<Int, List<String>, Boolean> {
        var guard: Guard? = null
        val visited: MutableList<Triple<Int, Int, String>> = mutableListOf()
        for (i in 0 until input.size) {
            for (j in 0 until input[i].length) {
                if (input[i][j] == '^') {
                    guard = Guard(j, i, '^')
                    break
                }
            }
            if (guard != null) {
                break
            }
        }
        if (guard == null) {
            return Triple(0, input, false)
        }
        var unchangedNumber = 0
        while (unchangedNumber < 5) {
            val change = guard.move(input)
            if (change && visited.contains(Triple(guard.x, guard.y, guard.direction) as Triple<Int, Int, String>)) {
                return Triple(0, input, true)
            } else if (change){
                visited.add(Triple(guard.x, guard.y, guard.direction) as Triple<Int, Int, String>)
            }
            if (change) {
                unchangedNumber = 0
            } else {
                unchangedNumber++
            }
            if (guard.x == input[0].length || guard.y == input.size) {
                break
            }
        }
        var count = 0
        for (l in input) {
            count += l.count { it == 'X' }
        }
        return Triple(count, input, false)
    }

    fun part2(input: List<String>, path: List<String>): Int {
        var possibleBlocks: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in 0 until path.size) {
            for (j in 0 until path[i].length) {
                if (path[i][j] == 'X') {
                    possibleBlocks.add(Pair(j, i))
                }
            }
        }
        var count = 0
        possibleBlocks.parallelStream()
            .forEach({ b ->
                val blockedInput = input.toMutableList()
                blockedInput[b.second] = blockedInput[b.second].addCharAtIndex('#', b.first)
                if (part1(blockedInput).third) {
                    count++
                }
            })
        return count
    }

    val testInput = readInput("Day${dayString}_test")
    part1(testInput.toMutableList()).first.println()

    val input = readInput("Day${dayString}")
    part1(input.toMutableList()).first.println()
    print("Part 2: ")
    part2(input, part1(input.toMutableList()).second).println()
}