package org.example

fun main() {
    genericsExample("string")
}

inline fun <reified T> genericsExample(value: T) {
    println(value)
    println("Type of T: ${T::class.java}")
}
class Reified {
}