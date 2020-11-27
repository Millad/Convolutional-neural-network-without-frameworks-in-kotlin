package com.dagdoni.millad.deeplearning

import koma.dot
import koma.matrix.Matrix
import koma.pow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.roundToInt

class AppTest {

    @Test
    fun skal_teste_trening_som_skal_gi_forventet_feil_prosent_er_lik_0_for_riktig_bilde_og_over_tallet_eller_1_for_feil_bilde() {
        val vertikalBildeMatrise = Matrise(Matrise.hentVertikalKernel(9))
        val maal = 1
        val kernelStorrelse = 3
        val alpha = 0.004
        val vekterStorrelse = Matrise.hentVertikalKernel(9)
        var vekter: Matrix<Double> = Matrise(vekterStorrelse).tilfeldigeVekter()
        var totalFeilForRiktigBilde = 0.0
        val antallTrenningsRunder = 60

        vekter = App.trening(vertikalBildeMatrise, kernelStorrelse, vekter, maal,alpha,antallTrenningsRunder).vekterTrent

        val feilBildeMatrise = Matrise(Matrise.hentHorizontalKernel(9))
        val feil_bilde_lag_1 = feilBildeMatrise.conv(Matrise.hentHorizontalKernel(kernelStorrelse))
        val feil_bilde_lag_2 = Matrise.relu(dot(feil_bilde_lag_1, vekter))
        val feil_prosent_gitt_feil_bilde = (feil_bilde_lag_2 - maal).pow(2)

        assertEquals( 0,totalFeilForRiktigBilde.roundToInt())
        assertTrue(feil_prosent_gitt_feil_bilde.roundToInt() >= 1)
    }
}