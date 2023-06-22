fun main() {
    val index = readln().toInt()
    val word = readln()
    if (index in word.indices) {
        println(word[index])
    } else {
        println("There isn't such an element in the given string, please fix the index!")
    }
}