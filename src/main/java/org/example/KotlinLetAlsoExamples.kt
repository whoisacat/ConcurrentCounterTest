package org.example

fun main(args: Array<String>) {

    val a : Any? = Any()
    val b : Any? = null

    a?.let{ func() } ?: run{ println("run") }
    a?.let{ fnull() } ?: run{ println("run") }
    b?.let{ func() } ?: run{ println("run") }
    b?.let{ fnull() } ?: run{ println("run") }

    a?.also{ func() } ?: run{ println("run") }
    a?.also{ fnull() } ?: run{ println("run") }
    b?.also{ func() } ?: run{ println("run") }
    b?.also{ fnull() } ?: run{ println("run") }

}

fun fnull() : Any? {
    println("fnull")
    return null
}

fun func() : Any? {
    println("func")
    return Any()
}

class KotlinLetAlsoExamples {

}