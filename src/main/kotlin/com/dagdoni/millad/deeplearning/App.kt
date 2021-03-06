package com.dagdoni.millad.deeplearning

import koma.dot
import koma.extensions.times
import koma.matrix.Matrix
import koma.pow


object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val vertikalBildematrise: Matrise = Bilde("vertikalLinje.png").hentMatrise()
        val mål = 1
        val alpha = 0.04
        val kernelStørrelse = 3
        val vekterStørrelse = 9

        val antallTreningsrunder = 8

        println("Trening :::::::::::::::::::::::::::::::::")
        val vekterTrent = tren(vertikalBildematrise, kernelStørrelse, mål, alpha, antallTreningsrunder, vekterStørrelse).vekterTrent

        println("Verifisering :::::::::::::::::::::::::::::::::")
        // Verifiser riktig bilde
        val riktig_bilde_lag_1 = vertikalBildematrise.conv(Matrise.hentVertikalKernel(kernelStørrelse))
        val riktig_bilde_lag_2 = Matrise.relu(dot(riktig_bilde_lag_1, vekterTrent))
        val feil_gitt_riktig_bilde = (riktig_bilde_lag_2 - mål).pow(2)
        println("Feil for riktig bilde       : ${feil_gitt_riktig_bilde}")

        // Verifiser feil bilde med høy error
        val horisontalBilde = Bilde("horizontalLinje.png")
        val feil_bilde_lag_1 = horisontalBilde.hentMatrise().conv(Matrise.hentHorizontalKernel(kernelStørrelse))
        val feil_bilde_lag_2 = Matrise.relu(dot(feil_bilde_lag_1, vekterTrent))
        val feil_gitt_feil_bilde = (feil_bilde_lag_2 - mål).pow(2)
        println("Feil for et helt annet bilde: ${feil_gitt_feil_bilde}")

        println("Programslutt :::::::::::::::::::::::::::::::::")
    }

    fun tren(vertikalBildematrise: Matrise, kernelStorrelse: Int, mål: Int, alpha: Double, antallOpplæringsrunder: Int, vekterStorrelse: Int): Treningsresultat {

        var vekter: Matrix<Double> = Matrise(vekterStorrelse).hentTilfeldigeVekter()
        var feil = 0.0
        (0 until antallOpplæringsrunder).forEach {
            // Fremoverforplantning
            val lag_1 = vertikalBildematrise.conv(Matrise.hentVertikalKernel(kernelStorrelse))
            val lag_2: Double = Matrise.relu(dot(lag_1, vekter))

            feil = (lag_2 - mål).pow(2)

            //Bakoverforplantning
            val lag_2_derivant = (lag_2 - mål) * Matrise.reluDerivant(lag_1)
            vekter -= (alpha * lag_2_derivant)
            println("${it} : ${feil}")
        }

        return Treningsresultat(feil, vekter)
    }

    data class Treningsresultat(val feil: Double, val vekterTrent: Matrix<Double>)
}
