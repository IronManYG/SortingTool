fun helpingTheRobot(purchases: Map<String, Int>, addition: Map<String, Int>): MutableMap<String, Int> {
    val newPurchases = purchases.toMutableMap()
    for ((k, v) in addition) {
        newPurchases[k] = if (newPurchases.containsKey(k)) purchases.getValue(k) + v else v
    }
    return newPurchases
}