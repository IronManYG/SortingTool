import java.util.Scanner

fun main() {
    val studentsMarks = mutableMapOf<String, Int>()
    val scanner = Scanner(System.`in`)
    while (scanner.hasNext()) {
        val key = scanner.next()
        if (key == "stop") {
            break
        } else {
            studentsMarks.putIfAbsent(key, scanner.nextInt())
        }
    }
    println(studentsMarks)
}