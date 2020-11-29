package com.dagdoni.millad.deeplearning

import koma.dot
import koma.extensions.times
import koma.matrix.Matrix
import koma.pow


object App {
        @JvmStatic
        fun main(args: Array<String>) {
            val vertikalBildeMatrise: Matrise = Bilde("vertikalLinje.png").somMatrise()
            val mål = 1
            val alpha = 0.04
            val kernelStorrelse = 3
            val vekterStorrelse = 9

            val antallTreningsrunder = 8

            println("Trening :::::::::::::::::::::::::::::::::")
            val vekterTrent = trening(vertikalBildeMatrise, kernelStorrelse,  mål, alpha,antallTreningsrunder, vekterStorrelse ).vekterTrent

            println("Verifisering :::::::::::::::::::::::::::::::::")
            // Verifiser riktig bildet
            val verifisering_lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val verifisering_lag_2 = Matrise.relu(dot(verifisering_lag_1, vekterTrent))
            val feil_prosent_gitt_riktig_bilde = (verifisering_lag_2 - mål).pow(2)
            println("Feil for riktig bilde       : ${feil_prosent_gitt_riktig_bilde}")

            // Verifiser feil bilde med høy error
            val horisontalBilde = Bilde("horizontalLinje.png")
            val feil_bilde_lag_1 = horisontalBilde.somMatrise().conv(Matrise.hentHorizontalKernel(kernelStorrelse))
            val feil_bilde_lag_2 = Matrise.relu(dot(feil_bilde_lag_1, vekterTrent))
            val feil_prosent_gitt_feil_bilde = (feil_bilde_lag_2 - mål).pow(2)
            println("Feil for et helt annet bilde: ${feil_prosent_gitt_feil_bilde}")

            println("Programslutt :::::::::::::::::::::::::::::::::")
        }

    fun trening(vertikalBildeMatrise: Matrise, kernelStorrelse: Int, mål: Int, alpha: Double, antallOpplæringsrunder:Int, vekterStorrelse:Int):TreningResultat {

        var vekter: Matrix<Double> = Matrise(vekterStorrelse).tilfeldigeVekter()
        var feil = 0.0
        (0 until antallOpplæringsrunder).forEach {
            // Fremoverforplantning
            val lag_1 = vertikalBildeMatrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val lag_2: Double = Matrise.relu(dot(lag_1, vekter))

            feil = (lag_2 - mål).pow(2)

            //Bakoverforplantning
            val lag_2_derivant = ((lag_2 - mål) * Matrise.reluDerivant(lag_1))
            vekter -= (alpha * lag_2_derivant)
            println("${it} : ${feil}")
        }

        return TreningResultat(feil, vekter)
    }

    data class TreningResultat(val feil:Double, val vekterTrent:Matrix<Double>)
}
