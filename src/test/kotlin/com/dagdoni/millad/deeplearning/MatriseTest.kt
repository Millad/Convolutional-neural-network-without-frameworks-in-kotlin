package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{

    @Test
    fun `skal kunne trekke fra to ulike matriser`(){
        // GITT
        val mat:Matrise = Matrise(1,1,0.0)
        val mat2:Matrise = Matrise(1,1,2.0)
        // NAAR
        val resultatMatrise:Matrise = mat.trekk(mat2)
        //DA
        assertThat(resultatMatrise.forstVerdi()).isEqualTo(2)
    }

    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0 for gitt matrise`(){
        // GITT
        val mat:Matrise = Matrise(1,1,1.0)
        val mat2:Matrise = Matrise(1,1,-2.0)
        // NAAR
        val talletEn:Matrise = mat.reluDerivant(mat)
        val talletNull:Matrise = mat.reluDerivant(mat2)
        //DA
        assertThat(talletEn.forstVerdi()).isEqualTo(1)
        assertThat(talletNull.forstVerdi()).isEqualTo(0)
    }

    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0`(){
        // GITT
        val mat:Matrise = Matrise()

        // NAAR
        val tallOver1:Double = mat.reluDerivant(2.5)
        val tallUnder0:Double = mat.reluDerivant(-1.0)
        //DA
        assertThat(tallOver1).isEqualTo(1.0)
        assertThat(tallUnder0).isEqualTo(0.0)
    }

    @Test
    fun `skal kun returnere positive tall fra relu operasjon`(){
        // GITT
        val mat:Matrise = Matrise(9,9)

        // NAAR
        val talletNull:Double = mat.relu(0.0)
        val talletEn:Double = mat.relu(1.0)
        //DA
        assertThat(talletNull).isEqualTo(0.0)
        assertThat(talletEn).isEqualTo(1.0)
    }

    @Test
    fun `skal kunne opprette riktig resultat conv operasjon`(){
        // GITT
        val mat:Matrise = Matrise(9,9)

        // NAAR
        val sumFraConvOperasjon:Matrise = mat.conv(7,3)

        //DA
        assertThat(sumFraConvOperasjon.erTom()).isFalse()
        assertThat(sumFraConvOperasjon.storrelse()).isEqualTo(Pair(3,3))
    }

    @Test
    fun `skal ikke kunne generere  matrise med tilferldig vekter om matrisen er tom`(){
        val matrise = Matrise()
        assertThat(matrise.hentForsteVerdi(0,0)).isEqualTo(0.0)
        matrise.tilfeldigeVekter()
        assertThat(matrise.erTom()).isTrue()
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
        val kernelMatrise:Matrise = mat.hentVertikalKernel(3,3)

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