fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val _numbers = numbers.toMutableList()
    _numbers.add(number)
    return _numbers
}