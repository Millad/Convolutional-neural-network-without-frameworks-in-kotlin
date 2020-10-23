package com.dagdoni.millad.deeplearning

import org.opencv.core.Mat


class App(val openCVAdapter: OpenCVAdapter) {
    fun hentMatriseFraBildet(navnPaBilde:String): Mat {
        return openCVAdapter.lesBildetSomMatrise(navnPaBilde)
    }
}

fun main(args: Array<String>) {
    val app:App = App(OpenCVAdapter())
    val mat: Mat = app.hentMatriseFraBildet("talletEn.png")
    println(mat.dump())
}
