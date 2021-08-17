package com.dagdoni.millad.deeplearning

import koma.dot
import koma.pow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.roundToInt

class AppTest {

    @Test
    fun `skal gi riktig bilde mindre feil andel enn feil bildet`() {
        val vertikalBildeMatrise = Matrise(Matrise.hentVertikalKernel(9))
        val mål = 1
        val kernelStorrelse = 3
        val alpha = 0.04
        val vekterStorrelse = 9
        val antallTrenningsrunder = 2

        val treningsresultatForRiktigBilde = App.tren(vertikalBildeMatrise, kernelStorrelse,  mål,alpha,antallTrenningsrunder, vekterStorrelse )

        val feilBildeMatrise = Matrise(Matrise.hentHorizontalKernel(9))
        val feil_bilde_lag_1 = feilBildeMatrise.conv(Matrise.hentHorizontalKernel(kernelStorrelse))
        val feil_bilde_lag_2 = Matrise.relu(dot(feil_bilde_lag_1, treningsresultatForRiktigBilde.vekterTrent))
        val feil_gitt_feil_bilde = (feil_bilde_lag_2 - mål).pow(2)

        assertTrue(treningsresultatForRiktigBilde.feil < feil_gitt_feil_bilde)
    }


    @Test
    fun skal_teste_trening_svarer_med_høy_feil_gitt_veldig_få_treningsrunder() {
        val vertikalBildeMatrise = Matrise(Matrise.hentVertikalKernel(9))
        val maal = 1
        val kernelStorrelse = 3
        val alpha = 0.04
        val vekterStorrelse = 9
        val antallTrenningsrunder = 1

        val treningsresultatForRiktigBilde = App.tren(vertikalBildeMatrise, kernelStorrelse,  maal,alpha,antallTrenningsrunder, vekterStorrelse )

        assertTrue(0 != treningsresultatForRiktigBilde.feil.roundToInt())
    }
}
