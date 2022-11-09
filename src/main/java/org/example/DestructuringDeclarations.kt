package org.example

fun main() {
    val (name, age) = DestructuringDeclarations()
    println(name)
    println(age)
}

class DestructuringDeclarations {
    operator fun component1(): String {
        return "component1()"
    }

    operator fun component2(): String {
        return "component2()"
    }

}