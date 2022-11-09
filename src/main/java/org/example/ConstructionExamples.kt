package org.example

fun main(args: Array<String>) {
    val example = Heritor("vb", "vc")
    println(example.let { it.a })
}
fun func1(): Int {
    return 2 + 3
}

fun func2(): Int {
    return 1 + 1
}

fun func0() : String {
    return "${func1()} ${func2()}"
}

open class ConstructionExamples
    constructor(var b : String = "b") {

    val a : String by lazy {
        func0()
    }
    init {
        b = "nb"
    }
}

class Heritor
    (b: String = "b")
    : ConstructionExamples(b) {
    var c : String
    constructor(vb: String, vc: String)
            : this() {
        b = vb
        c = vc
    }
    init {
        c = "nc"
    }
}