fun names(namesList: List<String>): List<String> {
    var count = 0
    val nameCount = mutableListOf<String>()
    for (name in namesList.distinct()) {
        count = namesList.count { it == name }
        nameCount.add("The name $name is repeated $count times")
    }
    return nameCount
}