package com.dagdoni.millad.deeplearning

import koma.dot
import koma.extensions.times
import koma.matrix.Matrix
import koma.pow


class App {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val vertikalBildeMatrise: Matrise = Bilde("vertikalLinje.png").somMatrise()
            val maalet = 1
            val alpha = 0.004
            val kernelStorrelse = 3
            val vekterStorrelse = 9
            var vekter: Matrix<Double> = Matrise(vekterStorrelse).tilfeldigeVekter()

            println("Trening :::::::::::::::::::::::::::::::::")
            // Trening
            (0 until 135).forEach {

                // Fremoverforplantning
                val lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
                val lag_2: Double = Matrise().relu(dot(lag_1, vekter))

                //cost
                val feil = (lag_2 - maalet).pow(2)

                //Bakoverforplantning
                val lag_2_derivant = (lag_2 - maalet) * (Matrise().reluDerivant(lag_1))
                vekter -= (alpha * lag_2_derivant)

                println("${it} : ${feil}")
            }

            println("Verifisering :::::::::::::::::::::::::::::::::")
            // Verifiser riktig bildet
            val v_lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val v_lag_2 = Matrise().relu(dot(v_lag_1, vekter))
            val feil_prosent_gitt_riktig_bilde = (v_lag_2 - maalet).pow(2)
            println("Feil for riktig bilde    : ${feil_prosent_gitt_riktig_bilde}")

            // Verifiser feil bilde med høy error
            val horisontalBilde = Bilde("horizontalLinje.png")
            val f_lag_1 = horisontalBilde.somMatrise().conv(Matrise.hentHorizontalKernel(kernelStorrelse))
            val f_lag_2 = Matrise().relu(dot(f_lag_1, vekter))
            val feil_prosent_gitt_feil_bilde = (f_lag_2 - maalet).pow(2)
            println("Feil for helt annet bilde: ${feil_prosent_gitt_feil_bilde}")

            println("Programslutt :::::::::::::::::::::::::::::::::")
        }
    }
}
