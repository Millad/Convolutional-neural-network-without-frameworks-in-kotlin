package com.dagdoni.millad.deeplearning

import koma.dot
import koma.pow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.roundToInt

class AppTest {

    @Test
    fun skal_teste_trening_svarer_0_for_riktig_bilde_og_over_tallet_1_for_feil_bilde() {
        val vertikalBildeMatrise = Matrise(Matrise.hentVertikalKernel(9))
        val maal = 1
        val kernelStorrelse = 3
        val alpha = 0.004
        val vekterStorrelse = 9
        val antallTrenningsrunder = 130

        val treningsresultat = App.trening(vertikalBildeMatrise, kernelStorrelse,  maal,alpha,antallTrenningsrunder, vekterStorrelse )

        val feilBildeMatrise = Matrise(Matrise.hentHorizontalKernel(9))
        val feil_bilde_lag_1 = feilBildeMatrise.conv(Matrise.hentHorizontalKernel(kernelStorrelse))
        val feil_bilde_lag_2 = Matrise.relu(dot(feil_bilde_lag_1, treningsresultat.vekterTrent))
        val feil_gitt_feil_bilde = (feil_bilde_lag_2 - maal).pow(2)

        assertEquals( 0,treningsresultat.feil.roundToInt())
        assertTrue(feil_gitt_feil_bilde.roundToInt() >= 1)
    }
}