package com.dagdoni.millad.deeplearning

import org.opencv.core.Mat


class App(val bilde: Bilde) {
    fun hentMatriseFraBildet(): Mat {
        return bilde.somMatrise()
    }
}

fun main(args: Array<String>) {
    val app:App = App(Bilde("talletEn.png"))
    val mat: Mat = app.hentMatriseFraBildet()
    println(mat.dump())
}
