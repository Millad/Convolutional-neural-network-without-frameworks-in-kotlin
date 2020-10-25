package com.dagdoni.millad.deeplearning

import kotlin.math.pow

class App(val bilde: Bilde) {

    fun hentBildeMatrise(): Matrise {
        return bilde.somMatrise()
    }
}
fun main(args: Array<String>) {
    val app = App(Bilde("talletEn.png"))
    val bildeMatrise: Matrise = app.hentBildeMatrise()
    val maalet = 1
    val alpha = 0.4

    val lag_1 = bildeMatrise.conv(7,3)
    var vekter:Matrise = Matrise(lag_1.storrelse()).tilfeldigeVekter()

    (0 until 10).forEach {
        // Fremoverforplantning
        val lag_2 = lag_1.relu(lag_1.dot(vekter))

        //cost
        val feil = (lag_2 - maalet).pow(2)

        //bakoverforplantning
       val lag_2_derivant = lag_1.reluDerivant(lag_1).multipliser((lag_2 - maalet))
        vekter = vekter.trekk(lag_2_derivant.multipliser(alpha))


        println("${it} : ${feil}")

    }
}
