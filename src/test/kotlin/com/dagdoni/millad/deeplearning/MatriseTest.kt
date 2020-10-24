package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{

    @Test
    fun `skal ikke kunne generere  matrise med tilferldig vekter om matrisen er tom`(){
        val matrise = Matrise()
        assertThat(matrise.hentForsteVerdi(0,0)).isEqualTo(0.0)
        matrise.tilfeldigeVekter()
        assertThat(matrise.erTom()).isTrue()
    }
    @Test
    fun `skal kunne generere  matrise med tilferldig vekter`(){
        val matrise = Matrise(3,3)
        assertThat(matrise.hentForsteVerdi(0,0)).isEqualTo(1.0)
        matrise.tilfeldigeVekter()
        assertThat(matrise.tilfeldigeVekter().hentForsteVerdi(2,2)).isGreaterThan(1.0)
    }
    @Test
    fun `skal kunne hente hele verdi`(){
        val matrise = Matrise(3,3)
        assertThat(matrise.hentVerdi(0,3,0,3).storrelse()).isEqualTo(matrise.storrelse())
    }
    @Test
    fun `skal kunne opprette et kernel og at kernal har en horizontal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val mat:Matrise = Matrise(3,3)

        // NAAR
        val kernelMatrise:Matrise = mat.hentMatriseMedHorizontalKernel(3,3)

        //DA
        assertThat(kernelMatrise.storrelse()).isEqualTo(Pair<Int,Int>(3,3))
        assertThat(kernelMatrise.hentForsteVerdi(0,0)).isEqualTo(0.0)
        assertThat(kernelMatrise.hentForsteVerdi(1,2)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentForsteVerdi(1,0)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentForsteVerdi(1,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentForsteVerdi(2,1)).isEqualTo(0.0)
        assertThat(kernelMatrise.hentForsteVerdi(2,2)).isEqualTo(0.0)
        assertThat(kernelMatrise.hentForsteVerdi(2,0)).isEqualTo(0.0)


    }

    @Test
    fun `skal kunne opprette et kernel og at kernal har en vertikal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val mat:Matrise = Matrise(3,3)

        // NAAR
        val kernelMatrise:Matrise = mat.hentMatriseMedVertikalKernel(3,3)

        //DA
        assertThat(kernelMatrise.storrelse()).isEqualTo(Pair<Int,Int>(3,3))
        assertThat(kernelMatrise.hentForsteVerdi(0,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentForsteVerdi(1,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.hentForsteVerdi(2,1)).isEqualTo(1.0)
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