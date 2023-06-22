fun main() {
    try {
        val string = "dskljdsaflkj"
        val num = string.toInt()

        // do not touch the lines below
    } catch (e: RuntimeException) {
        println("Well")
    } catch (e: Exception) {
        println("Wrong")
    }
}