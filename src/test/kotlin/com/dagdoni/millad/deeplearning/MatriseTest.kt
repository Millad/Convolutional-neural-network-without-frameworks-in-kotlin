package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{

    @Test
    fun `gitt 1 px matrise, skal man kunne returnere første innholdet i matrisen`() {
        val mat:Matrise = Matrise(1,1)
        assertThat(mat.somBitwiseNot().forstVerdi()).isEqualTo(1)
    }
    @Test
    fun `gitt svart og hvit bilde, skal det returneres 1 (svart)  isteden for 255 og 0 (hvit) isteden for 1 som en bitwise_not operasjon`() {
        val mat:Matrise = Matrise(1,1)
        assertThat(mat.somBitwiseNot().erTom()).isFalse()
    }

    @Test
    fun `skal kunne lage en tom matrise`(){
        val matrise = Matrise(0,0)
        assertThat(matrise.erTom()).isTrue()
        assertThat(matrise.somBitwiseNot().forstVerdi()).isEqualTo(0)
    }
    @Test
    fun `skal kunne returnere størrelse på matrisen`(){
        val matrise = Matrise(1,1)

        assertThat(matrise.erTom()).isFalse()
    }

}