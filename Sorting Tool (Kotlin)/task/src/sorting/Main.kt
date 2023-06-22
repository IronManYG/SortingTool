package sorting

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val sortingTool = SortingTool()
    sortingTool.processArgs(args)
}

sealed interface Arguments {
    data class DataType(val text: String = "-dataType") : Arguments
    data class SortingType(val text: String = "-sortingType") : Arguments
    data class InputFile(val text: String = "-inputFile") : Arguments
    data class OutputFile(val text: String = "-outputFile") : Arguments
}

sealed interface DataType {
    data class Word(val text: String = "word") : DataType
    data class Long(val text: String = "long") : DataType
    data class Line(val text: String = "line") : DataType
}

sealed interface SortingType {
    data class Natural(val text: String = "natural") : SortingType
    data class ByCount(val text: String = "byCount") : SortingType
}

class SortingTool {
    private val scanner = Scanner(System.`in`)
    private val wordList = mutableListOf<String>()
    private val numberList = mutableListOf<Int>()
    private val lineList = mutableListOf<String>()
    private val arguments = mutableMapOf(
        Arguments.DataType().text to DataType.Word().text,
        Arguments.SortingType().text to SortingType.Natural().text,
        Arguments.InputFile().text to "",
        Arguments.OutputFile().text to "",
    )
    private val workingDirectory = System.getProperty("user.dir")
    private val separator = File.separator
    private var outPutFileName = ""
    private var absolutePath = ""
    private var textToFile = ""

    fun processArgs(args: Array<String>) {
        if (args.isNotEmpty()) {
            args.find { it == Arguments.DataType().text }.also {
                if (it != null) {
                    try {
                        if (args[args.indexOf(it) + 1] != Arguments.SortingType().text) {
                            arguments[it] = args[args.indexOf(it) + 1]
                        } else throw Exception()
                    } catch (e: Exception) {
                        println("No data type defined!")
                    }
                }
            }
            args.find { it == Arguments.SortingType().text }.also {
                if (it != null) {
                    try {
                        if (args[args.indexOf(it) + 1] != Arguments.DataType().text) {
                            arguments[it] = args[args.indexOf(it) + 1]
                        } else throw Exception()
                    } catch (e: Exception) {
                        println("No sorting type defined!")
                    }
                }
            }
            args.find { it == Arguments.InputFile().text }.also {
                if (it != null) {
                    try {
                        if (args[args.indexOf(it) + 1] != Arguments.InputFile().text) {
                            arguments[it] = args[args.indexOf(it) + 1]
                        } else throw Exception()
                    } catch (e: Exception) {
                        println("No input file defined!")
                    }
                }
            }
            args.find { it == Arguments.OutputFile().text }.also {
                if (it != null) {
                    try {
                        if (args[args.indexOf(it) + 1] != Arguments.OutputFile().text) {
                            arguments[it] = args[args.indexOf(it) + 1]
                        } else throw Exception()
                    } catch (e: Exception) {
                        println("No output file defined!")
                    }
                }
            }

            val regex = Regex("-(.*)")
            args.find { regex.matches(it) && it !in arguments.keys }.also {
                if (it != null) {
                    println("$it is not a valid parameter. It will be skipped.")
                }
            }
            sortValues()
        }
    }

    private fun sortValues() {
        outPutFileName = arguments[Arguments.OutputFile().text].toString()
        absolutePath = "${workingDirectory}${separator}$outPutFileName"
        when (arguments[Arguments.DataType().text]) {
            DataType.Word().text -> {
                sortWords()
            }

            DataType.Long().text -> {
                sortNumerics()
            }

            DataType.Line().text -> {
                sortLines()
            }
        }
    }

    private fun sortWords() {
        processInputData(list = wordList, dataType = DataType.Word())
        processOutPutData(dataType = DataType.Word())
    }

    private fun sortNumerics() {
        processInputData(numericList = numberList, dataType = DataType.Long())
        processOutPutData(dataType = DataType.Long())
    }

    private fun sortLines() {
        processInputData(list = lineList, dataType = DataType.Line())
        processOutPutData(dataType = DataType.Line())
    }

    private fun itemPercentageInList(item: String, list: List<String>): Int {
        return (list.count { it == item }.toDouble() / list.size.toDouble() * 100).toInt()
    }

    private fun processInputData(
        list: MutableList<String> = mutableListOf(),
        numericList: MutableList<Int> = mutableListOf(),
        dataType: DataType
    ) {
        if (arguments[Arguments.InputFile().text].isNullOrEmpty()) {
            while (scanner.hasNext()) {
                when (dataType) {
                    is DataType.Word -> {
                        list.add(scanner.next())
                    }

                    is DataType.Long -> {
                        numericList.add(scanner.nextInt())
                    }

                    is DataType.Line -> {
                        list.add(scanner.nextLine())
                    }
                }
            }
        } else {
            val fileName = arguments[Arguments.InputFile().text]
            val inputFile = File(fileName!!)
            when (dataType) {
                is DataType.Word -> {
                    inputFile.forEachLine {
                        for (word in it.split(" ")) {
                            if (word.isNotEmpty()) {
                                list.add(word)
                            }
                        }
                    }
                }

                is DataType.Long -> {
                    val regex = Regex("[0-9]+")
                    inputFile.forEachLine {
                        if (regex.matches(it)) numericList.add(it.toInt())
                    }
                }

                is DataType.Line -> {
                    list.addAll(inputFile.readLines())
                }
            }
        }
    }

    private fun processOutPutData(dataType: DataType) {
        val totalData: String
        val sortedDataNatural: String
        var sortedDataByCount = ""
        if (arguments[Arguments.SortingType().text] == SortingType.Natural().text) {
            when (dataType) {
                is DataType.Word -> {
                    totalData = "Total words: ${wordList.size}."
                    sortedDataNatural = "Sorted data: ${wordList.sorted().joinToString(" ")}"
                }

                is DataType.Long -> {
                    totalData = "Total numbers: ${numberList.size}."
                    sortedDataNatural = "Sorted data: ${numberList.sorted().joinToString(" ")}"
                }

                is DataType.Line -> {
                    totalData = "Total lines: ${lineList.size}."
                    sortedDataNatural = "Sorted data:\n${lineList.sorted()}"
                }
            }
            if (arguments[Arguments.OutputFile().text].isNullOrEmpty()) {
                println(totalData)
                println(sortedDataNatural)
            } else {
                textToFile = buildString {
                    textToFile = buildString {
                        appendLine(totalData)
                        append(sortedDataNatural)
                    }
                    File(absolutePath).writeText(textToFile)
                }
            }
        } else if (arguments[Arguments.SortingType().text] == SortingType.ByCount().text) {
            when (dataType) {
                is DataType.Word -> {
                    totalData = "Total words: ${wordList.size}."
                    wordList.sorted().sortedBy { word ->
                        itemPercentageInList(
                            item = word,
                            list = wordList
                        )
                    }.toSet().forEach { word ->
                        val wordPercentage =
                            itemPercentageInList(item = word, list = wordList)
                        sortedDataByCount += "$word: ${wordList.count { it == word }} time(s), $wordPercentage%\n"
                    }
                }

                is DataType.Long -> {
                    totalData = "Total numbers: ${numberList.size}."
                    numberList.sorted().sortedBy { num ->
                        itemPercentageInList(
                            item = num.toString(),
                            list = numberList.map { it.toString() }
                        )
                    }.toSet().forEach { num ->
                        val numPercentage =
                            itemPercentageInList(item = num.toString(), list = numberList.map { it.toString() })
                        sortedDataByCount += "$num: ${numberList.count { it == num }} time(s), $numPercentage%\n"
                    }
                }

                is DataType.Line -> {
                    totalData = "Total lines: ${lineList.size}."
                    lineList.sorted().sortedBy { line ->
                        itemPercentageInList(
                            item = line,
                            list = lineList
                        )
                    }.toSet().forEach { line ->
                        val wordPercentage =
                            itemPercentageInList(item = line, list = lineList)
                        sortedDataByCount += "$line: ${lineList.count { it == line }} time(s), $wordPercentage%\n"
                    }
                }
            }
            if (arguments[Arguments.OutputFile().text].isNullOrEmpty()) {
                println(totalData)
                println(sortedDataByCount)
            } else {
                textToFile = sortedDataByCount
                File(absolutePath).writeText(textToFile)
            }
        }
    }
}
