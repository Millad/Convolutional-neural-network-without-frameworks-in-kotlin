package com.dagdoni.millad.deeplearning

import koma.end
import koma.extensions.get
import koma.matrix.Matrix
import koma.util.test.assertMatrixEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.math.absoluteValue

class MatriseTest{

    @Test
    fun `skal kunne hente riktig verdier fra bildetmatrisen basert på liten kernel størrelse`(){
        // GITT
        val mat:Matrix<Double> =    koma.mat[1,1,2,2 end
                                             1,1,2,2 end
                                             3,3,4,4 end
                                             3,3,4,4 ]

        // NÅR
        val matrixFraConvOperasjon = Matrise(mat).hentMatriseStriveForKernel(2)

        // SÅ
        val forventet1:Matrix<Double> =   koma.mat[1,1 end
                                                  1,1]
        val forventet2:Matrix<Double> =   koma.mat[2,2 end
                                                  2,2]
        val forventet3:Matrix<Double> =   koma.mat[3,3 end
                                                   3,3]
        val forventet4:Matrix<Double> =   koma.mat[4,4 end
                                                  4,4]

        assertMatrixEquals(forventet1,matrixFraConvOperasjon.first())
        assertMatrixEquals(forventet2, matrixFraConvOperasjon[1])
        assertMatrixEquals(forventet3, matrixFraConvOperasjon[2])
        assertMatrixEquals(forventet4, matrixFraConvOperasjon[3])
    }

    @Test
    fun `skal kunne opprette riktig resultat conv operasjon for vertikal ganger vertikal kernet`(){
        // GITT
        val mat:Matrix<Double> =    koma.mat[0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0]

        // NÅR
        val matrixFraConvOperasjon:Matrix<Double> = Matrise(mat).conv(Matrise.hentVertikalKernel(2))

        // SÅ

        assertThat(matrixFraConvOperasjon.get(0,0).absoluteValue).isEqualTo(2.0)
    }


    @Test
    fun `skal kunne opprette riktig resultat conv operasjon for horisontal ganger vertikal kernet`(){
        // GITT
        val mat:Matrix<Double> =    koma.mat[0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0]

        // NÅR
        val matrixFraConvOperasjon:Matrix<Double> = Matrise(mat).conv(Matrise.hentHorizontalKernel(2))

        // SÅ

        assertThat(matrixFraConvOperasjon.get(0,0).absoluteValue).isEqualTo(4.0)
    }


    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0 for gitt matrise`(){
        // GITT
        val mat:Matrix<Double> = Matrise(1,1,1.0).matrise()
        val mat2:Matrix<Double>  = Matrise(1,1,-2.0).matrise()
        // NÅR
        val talletEn:Matrix<Double> = Matrise().reluDerivant(mat)
        val talletNull:Matrix<Double> = Matrise().reluDerivant(mat2)
        // SÅ
        assertThat(talletEn.get(0,0)).isEqualTo(1.0)
        assertThat(talletNull.get(0,0)).isEqualTo(0.0)
    }

    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0`(){
        // GITT
        val mat:Matrise = Matrise()

        // NÅR
        val tallOver1:Double = mat.reluDerivant(2.5)
        val tallUnder0:Double = mat.reluDerivant(-1.0)
        // SÅ
        assertThat(tallOver1).isEqualTo(1.0)
        assertThat(tallUnder0).isEqualTo(0.0)
    }

    @Test
    fun `skal kun returnere positive tall fra relu operasjon`(){
        // GITT
        val mat:Matrise = Matrise(9,9)

        // NÅR
        val talletNull:Double = mat.relu(0.0)
        val talletEn:Double = mat.relu(1.0)
        // SÅ
        assertThat(talletNull).isEqualTo(0.0)
        assertThat(talletEn).isEqualTo(1.0)
    }

    @Test
    fun `skal kunne opprette et kernel og at kernal har en horizontal linje med tallet 1 omringet ellers med 0`(){
        // GITT
        val kernelMatrise:Matrix<Double> = Matrise.hentHorizontalKernel(3)

        // SÅ
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

        //NÅR
        val kernelMatrise:Matrix<Double> = Matrise.hentVertikalKernel(3)

        // SÅ
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