package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{

    @Test
    fun `skal kunne returnere størrelse på matrisen`(){
        val matrise = Matrise(1,1)

        assertThat(matrise.erTom()).isFalse()
    }

}