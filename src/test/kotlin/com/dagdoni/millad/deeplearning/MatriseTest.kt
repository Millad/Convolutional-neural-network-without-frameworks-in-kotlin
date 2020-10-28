package com.dagdoni.millad.deeplearning

import koma.extensions.get
import koma.matrix.Matrix
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatriseTest{


    @Test
    fun `skal kunne opprette riktig resultat conv operasjon`(){
        // GITT
        val mat:Matrise = Matrise(9,9)

        // NAAR
        val matrixFraConvOperasjon:Matrix<Double> = mat.conv(3,mat.hentHorizontalKernel(3))

        //DA
        assertThat(matrixFraConvOperasjon.numRows()).isEqualTo(3)
        assertThat(matrixFraConvOperasjon.numCols()).isEqualTo(1)
    }

    @Test
    fun `skal kunne trekke fra to ulike matriser`(){
        // GITT
        val mat:Matrise = Matrise(1,1,0.0)
        val mat2:Matrise = Matrise(1,1,2.0)
        // NAAR
        val resultatMatrise:Matrise = mat.trekk(mat2)
        //DA
        assertThat(resultatMatrise.forstVerdi()).isEqualTo(-2.0)
    }

    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0 for gitt matrise`(){
        // GITT
        val mat:Matrix<Double> = Matrise(1,1,1.0).matrise()
        val mat2:Matrix<Double>  = Matrise(1,1,-2.0).matrise()
        // NAAR
        val talletEn:Matrix<Double> = Matrise().reluDerivant(mat)
        val talletNull:Matrix<Double> = Matrise().reluDerivant(mat2)
        //DA
        assertThat(talletEn.get(0,0)).isEqualTo(1.0)
        assertThat(talletNull.get(0,0)).isEqualTo(0.0)
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
    fun `skal kunne opprette et kernel og at kernal har en horizontal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val mat:Matrise = Matrise(3,3)

        // NAAR
        val kernelMatrise:Matrix<Double> = mat.hentHorizontalKernel(3)

        //DA
        assertThat(kernelMatrise.get(0,0)).isEqualTo(0.0)
        assertThat(kernelMatrise.get(1,2)).isEqualTo(1.0)
        assertThat(kernelMatrise.get(1,0)).isEqualTo(1.0)
        assertThat(kernelMatrise.get(1,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.get(2,1)).isEqualTo(0.0)
        assertThat(kernelMatrise.get(2,2)).isEqualTo(0.0)
        assertThat(kernelMatrise.get(2,0)).isEqualTo(0.0)
    }

    @Test
    fun `skal kunne opprette et kernel og at kernal har en vertikal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val mat:Matrise = Matrise(3,3)

        // NAAR
        val kernelMatrise:Matrix<Double> = mat.hentVertikalKernel(3)

        //DA
        assertThat(kernelMatrise.get(0,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.get(1,1)).isEqualTo(1.0)
        assertThat(kernelMatrise.get(2,1)).isEqualTo(1.0)
    }


    @Test
    fun `skal kunne returnere størrelse på matrisen`(){
        val matrise = Matrise(1,1)
        assertThat(matrise.erTom()).isFalse()
    }

}