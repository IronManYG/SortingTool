fun solution() {
    val firstNum = readln().toInt()
    val secondNum = readln().toInt()
    try {
        println(firstNum / secondNum)
    } catch (e: Exception) {
        println(e.message)
    } finally {
        println("This is the end, my friend.")
    }
}