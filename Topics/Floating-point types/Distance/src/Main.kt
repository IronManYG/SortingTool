fun main() {
    val distanceBetweenCities = readln().toDouble()
    val travelTimeInHours = readln().toDouble()
    val averageSpeed = distanceBetweenCities / travelTimeInHours
    println(averageSpeed)
}