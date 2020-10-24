package com.dagdoni.millad.deeplearning

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{


    @Test
    fun `skal kunne opprette et kernel og at kernal har en vertikal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val mat:Matrise = Matrise(3,3)

        // NAAR
        val kernelMatrise:Matrise = mat.lagKernelVertikalLinje()

        //DA
        assertThat(kernelMatrise.storrelse()).isEqualTo(Pair<Int,Int>(3,3))
        assertThat(kernelMatrise.hentVerdi(0,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentVerdi(1,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentVerdi(2,1)).isEqualTo(1.0)
    }

    @Test
    fun `gitt 1 px matrise, skal man kunne returnere første innholdet i matrisen`() {
        val mat:Matrise = Matrise(1,1)
        assertThat(mat.somBitwiseNot().forstVerdi()).isEqualTo(1)
    }
    @Test
    fun `gitt svart og hvit bilde, skal det returneres 1 (svart)  isteden for 255 og 0 (hvit) isteden for 1 som en bitwise_not operasjon`() {
        val mat:Matrise = Matrise(1,1)
        assertThat(mat.somBitwiseNot().erTom()).isFalse()
        assertThat(mat.type()).isEqualTo("CV_8UC1")
    }

    @Test
    fun `skal kunne lage en tom matrise`(){
        val matrise = Matrise()
        assertThat(matrise.erTom()).isTrue()
        assertThat(matrise.somBitwiseNot().forstVerdi()).isEqualTo(0)
    }
    @Test
    fun `skal kunne returnere størrelse på matrisen`(){
        val matrise = Matrise(1,1)
        assertThat(matrise.erTom()).isFalse()
    }

}