fun summator(map: Map<Int, Int>): Int {
    var sum = 0
    for ((k, v) in map) {
        if (k.mod(2) == 0) {
            sum += v
        }
    }
    return sum
}