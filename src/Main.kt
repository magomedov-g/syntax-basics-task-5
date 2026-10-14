fun main() {
    println("Проверка: существует ли целое y, такое что x^y = n")

    while (true) {
        print("\nВведите число n и основание x через пробел: ")
        val input = readLine()

        // Проверка на выход из программы
        if (input == "exit" || input == "выход") {
            println("Программа завершена.")
            break
        }

        if (input.isNullOrBlank()) {
            println("Ошибка: Пустой ввод")
            continue
        }
        try {
            val (n, x) = parseInput(input)
            val result = findIntegerExponent(n, x)
            if (result != null) {
                println("✅ Результат: $x^$result = $n")
                println("Целочисленный показатель: y = $result")
            } else {
                println("Целочисленный показатель не существует")
            }

        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
        }
    }
}
fun parseInput(input: String): Pair<Int, Int> {
    val parts = input.trim().split(" ").filter { it.isNotBlank() }
    if (parts.size != 2) {
        throw IllegalArgumentException("Неверный формат. Введите два целых числа через пробел")
    }
    val n = parts[0].toIntOrNull()
        ?: throw IllegalArgumentException("Число n должно быть целым")
    val x = parts[1].toIntOrNull()
        ?: throw IllegalArgumentException("Основание x должно быть целым")
    if (x <= 0) {
        throw IllegalArgumentException("Основание x должно быть положительным числом")
    }

    if (x == 1) {
        throw IllegalArgumentException("Основание x не может быть равно 1")
    }
    return Pair(n, x)
}
fun findIntegerExponent(n: Int, x: Int): Int? {
    when {
        n == 1 -> return 0  // x^0 = 1 для любого x
        n == x -> return 1  // x^1 = x
        n <= 0 -> return null // Для положительного x результат всегда положителен
    }

    var y = 0
    var power = 1
    while (power <= n) {
        if (power == n) {
            return y
        }
        if (power > Int.MAX_VALUE / x) {
            break
        }
        y++
        power *= x
    }
    return null
}
fun findIntegerExponentWithLog(n: Int, x: Int): Int? {
    if (n <= 0) return null
    val logResult = Math.log(n.toDouble()) / Math.log(x.toDouble())
    val rounded = Math.round(logResult).toInt()
    return if (Math.pow(x.toDouble(), rounded.toDouble()).toInt() == n) {
        rounded
    } else {
        null
    }
}