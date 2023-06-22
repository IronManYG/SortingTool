const val gravityConstant = 9.8 // in m/s²

fun main() {
    val densityOfALiquid = readln().toDouble()
    val heightOfAColumn = readln().toDouble()
    val liquidPressure = gravityConstant * densityOfALiquid * heightOfAColumn
    println(liquidPressure)
}