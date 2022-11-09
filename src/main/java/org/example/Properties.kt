package org.example

fun main() {
    val o = Properties()
    println(o.a)
}
class Properties {

    var a: String = "null"
        get() {
            return field.trim()
        }
        set(value) {
            field = value.trim()
        }
}