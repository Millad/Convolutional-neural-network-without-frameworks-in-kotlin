package com.dagdoni.millad.deeplearning

import koma.dot
import koma.matrix.Matrix
import koma.pow
import koma.extensions.times

fun main(args: Array<String>) {
    val bilde = Bilde("ver.png")
    val vertikalBilde: Matrise = bilde.somMatrise()
    val maalet = 1
    val alpha = 0.004
    val kernelStorrelse = 9

    var vekter: Matrix<Double> = Matrise(kernelStorrelse).tilfeldigeVekter()


    (0 until 20).forEach {

        // Fremoverforplantning
        val lag_1 = vertikalBilde.conv(kernelStorrelse,Matrise().hentVertikalKernel(kernelStorrelse))
        val lag_2:Double = Matrise().relu(dot(lag_1,vekter))

        //cost
        val feil = (lag_2 - maalet).pow(2)

        //bakoverforplantning
       val lag_2_derivant =  (lag_2 - maalet) * (Matrise().reluDerivant(lag_1))
        vekter -= (alpha * lag_2_derivant )

        println("${it} : ${feil}")
    }

    // Verifiser riktig bildet
    val v_lag_1 = vertikalBilde.conv(kernelStorrelse,Matrise().hentVertikalKernel(kernelStorrelse))
    val v_lag_2 = Matrise().relu(dot(v_lag_1,vekter))
    val feil_prosent_gitt_riktig_bilde = (v_lag_2 - maalet).pow(2)
    println("Riktig bilde error: ${feil_prosent_gitt_riktig_bilde}")

    // Verifiser feil bilde med høy error
    val horisontalBilde = Bilde("hor.png")
    val f_lag_1 = horisontalBilde.somMatrise().conv(kernelStorrelse,Matrise().hentHorizontalKernel(kernelStorrelse))
    val f_lag_2 = Matrise().relu(dot(f_lag_1,vekter))
    val feil_prosent_gitt_feil_bilde = (f_lag_2 - maalet).pow(2)
    println("Feil bilde error  : ${feil_prosent_gitt_feil_bilde}")
}
