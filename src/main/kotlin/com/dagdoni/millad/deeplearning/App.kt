package com.dagdoni.millad.deeplearning

import koma.dot
import koma.extensions.times
import koma.matrix.Matrix
import koma.pow


object App {
        @JvmStatic
        fun main(args: Array<String>) {
            val vertikalBildeMatrise: Matrise = Bilde("vertikalLinje.png").somMatrise()
            val maal = 1
            val alpha = 0.004
            val kernelStorrelse = 3
            val vekterStorrelse = 9
            var vekter: Matrix<Double> = Matrise(vekterStorrelse).tilfeldigeVekter()
            val antallTreningsrunder = 135

            println("Trening :::::::::::::::::::::::::::::::::")
            vekter = trening(vertikalBildeMatrise, kernelStorrelse, vekter, maal, alpha,antallTreningsrunder ).vekterTrent

            println("Verifisering :::::::::::::::::::::::::::::::::")
            // Verifiser riktig bildet
            val v_lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val v_lag_2 = Matrise.relu(dot(v_lag_1, vekter))
            val feil_prosent_gitt_riktig_bilde = (v_lag_2 - maal).pow(2)
            println("Feil for riktig bilde       : ${feil_prosent_gitt_riktig_bilde}")

            // Verifiser feil bilde med høy error
            val horisontalBilde = Bilde("horizontalLinje.png")
            val f_lag_1 = horisontalBilde.somMatrise().conv(Matrise.hentHorizontalKernel(kernelStorrelse))
            val f_lag_2 = Matrise.relu(dot(f_lag_1, vekter))
            val feil_prosent_gitt_feil_bilde = (f_lag_2 - maal).pow(2)
            println("Feil for et helt annet bilde: ${feil_prosent_gitt_feil_bilde}")

            println("Programslutt :::::::::::::::::::::::::::::::::")
        }

    fun trening(vertikalBildeMatrise: Matrise, kernelStorrelse: Int, vekter: Matrix<Double>, maal: Int, alpha: Double, antallOpplæringsrunder:Int):TreningResultat {

        var vekterForEnTreningsrunde = vekter
        var feilForEnTreningssrunde = 0.0
        (0 until antallOpplæringsrunder).forEach {
            // Fremoverforplantning
            val lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val lag_2: Double = Matrise.relu(dot(lag_1, vekterForEnTreningsrunde))

            feilForEnTreningssrunde = (lag_2 - maal).pow(2)

            //Bakoverforplantning
            val lag_2_derivant = (lag_2 - maal) * (Matrise.reluDerivant(lag_1))
            vekterForEnTreningsrunde -= (alpha * lag_2_derivant)
            println("${it} : ${feilForEnTreningssrunde}")
        }

        return TreningResultat(feilForEnTreningssrunde, vekterForEnTreningsrunde)
    }

    data class TreningResultat(val feil:Double, val vekterTrent:Matrix<Double>)
}
