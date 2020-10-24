package com.dagdoni.millad.deeplearning

import org.opencv.core.Mat


class App(val bilde: Bilde) {
    fun hentMatriseFraBildet(): Matrise {
        return bilde.somMatrise()
    }
}

fun main(args: Array<String>) {
    val app:App = App(Bilde("talletEn.png"))
    val mat: Matrise = app.hentMatriseFraBildet()
    println(mat)
}
