package com.dagdoni.millad.deeplearning

import koma.matrix.Matrix
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.roundToInt

class AppTest {

    @Test
    fun run() {
        val vertikalBildeMatrise = Matrise(Matrise.hentVertikalKernel(9))
        val maal = 1
        val kernelStorrelse = 3
        val alpha = 0.004
        val vekterStorrelse = Matrise.hentVertikalKernel(9)
        var vekter: Matrix<Double> = Matrise(vekterStorrelse).tilfeldigeVekter()
        var totalFeil = 0.0
        (0 until 60).forEach {
           val resultat = App.opplæringsrunde(vertikalBildeMatrise, kernelStorrelse, vekter, maal,alpha)
            totalFeil = resultat.feil
            vekter = resultat.vekter
        }
        assertEquals( 0,totalFeil.roundToInt())
    }
}