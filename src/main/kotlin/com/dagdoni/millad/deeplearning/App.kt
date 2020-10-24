package com.dagdoni.millad.deeplearning

class App(val bilde: Bilde) {
    fun hentMatriseFraBildet(): Matrise {
        return bilde.somMatrise()
    }
}
//TODO Eksperimenter med mindre kernel storrelse
fun main(args: Array<String>) {
    val app = App(Bilde("talletEn.png"))
    val mat: Matrise = app.hentMatriseFraBildet()
    println(mat)
}
