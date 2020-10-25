package com.dagdoni.millad.deeplearning

import kotlin.math.pow

fun main(args: Array<String>) {
    val bilde = Bilde("talletEn.png")
    val bildeMatrise: Matrise = bilde.somMatrise()
    val maalet = 1
    val alpha = 0.0000001

    val lag_1 = bildeMatrise.conv(7,3)
    var vekter:Matrise = Matrise(lag_1.storrelse()).tilfeldigeVekter()



    (0 until 2).forEach {
        // Fremoverforplantning
        val lag_2 = lag_1.relu(lag_1.dot(vekter))

        //cost
        val feil = (lag_2 - maalet).pow(2)

        //bakoverforplantning
       val lag_2_derivant = lag_1.reluDerivant(lag_1).multipliser((lag_2 - maalet)) // FEIL HER
        vekter = vekter.trekk(lag_2_derivant.multipliser(alpha))

        println("${it} : ${feil}")
    }

    // Verifiser riktig bildet
    val v_lag_1 = bildeMatrise.conv(7,3)
    val v_lag_2 = v_lag_1.relu(v_lag_1.dot(vekter))
    val feil_prosent_gitt_riktig_bilde = (v_lag_2 - maalet).pow(2)
    println("Feil: ${feil_prosent_gitt_riktig_bilde}")

}
