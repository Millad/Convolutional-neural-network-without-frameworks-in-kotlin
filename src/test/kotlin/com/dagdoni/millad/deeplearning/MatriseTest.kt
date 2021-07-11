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
    fun `skal kunne hente riktig verdier fra bildematrise basert på liten kernel størrelse`(){
        // GITT
        val mat:Matrix<Double> =    koma.mat[1,1,2,2 end
                                             1,1,2,2 end
                                             3,3,4,4 end
                                             3,3,4,4 ]

        // NÅR
        val matriseEtterStrideOperasjon = Matrise(mat).hentMatriserFraStridesOperasjon(2)

        // SÅ
        val forventet1:Matrix<Double> =   koma.mat[1,1 end
                                                  1,1]
        val forventet2:Matrix<Double> =   koma.mat[2,2 end
                                                  2,2]
        val forventet3:Matrix<Double> =   koma.mat[3,3 end
                                                   3,3]
        val forventet4:Matrix<Double> =   koma.mat[4,4 end
                                                  4,4]

        assertMatrixEquals(forventet1,matriseEtterStrideOperasjon.first())
        assertMatrixEquals(forventet2, matriseEtterStrideOperasjon[1])
        assertMatrixEquals(forventet3, matriseEtterStrideOperasjon[2])
        assertMatrixEquals(forventet4, matriseEtterStrideOperasjon[3])
    }

    @Test
    fun `skal kunne opprette riktig resultat conv operasjon for vertikal ganger vertikal kernet`(){
        val mat:Matrix<Double> =    koma.mat[0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0]

        val matrixFraConvOperasjon:Matrix<Double> = Matrise(mat).conv(Matrise.hentVertikalKernel(2))

        assertThat(matrixFraConvOperasjon.get(0,0).absoluteValue).isEqualTo(2.0)
    }


    @Test
    fun `skal kunne opprette riktig resultat conv operasjon for horisontal ganger vertikal kernet`(){

        val mat:Matrix<Double> =    koma.mat[0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0 end
                                            0,1,1,0]

        val matrixFraConvOperasjon:Matrix<Double> = Matrise(mat).conv(Matrise.hentHorizontalKernel(2))

        assertThat(matrixFraConvOperasjon.get(0,0).absoluteValue).isEqualTo(4.0)
    }


    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0 for gitt matrise`(){
        // GITT
        val mat:Matrix<Double> = Matrise(1,1,1.0).hentMatrise()
        val mat2:Matrix<Double>  = Matrise(1,1,-2.0).hentMatrise()
        // NÅR
        val talletEn:Matrix<Double> = Matrise.reluDerivant(mat)
        val talletNull:Matrix<Double> = Matrise.reluDerivant(mat2)
        // SÅ
        assertThat(talletEn.get(0,0)).isEqualTo(1.0)
        assertThat(talletNull.get(0,0)).isEqualTo(0.0)
    }

    @Test
    fun `skal kun returnere 1 om tall er storre enn 0 ellers returner 0`(){
        val tallOver1:Double = Matrise.reluDerivant(2.5)
        val tallUnder0:Double = Matrise.reluDerivant(-1.0)

        assertThat(tallOver1).isEqualTo(1.0)
        assertThat(tallUnder0).isEqualTo(0.0)
    }

    @Test
    fun `skal kun returnere positive tall fra relu operasjon`(){
        val talletNull:Double = Matrise.relu(0.0)
        val talletEn:Double = Matrise.relu(1.0)

        assertThat(talletNull).isEqualTo(0.0)
        assertThat(talletEn).isEqualTo(1.0)
    }

    @Test
    fun `skal kunne opprette et kernel og at kernal har en horizontal linje med tallet 1 omringet ellers med 0`(){
        val kernelMatrise:Matrix<Double> = Matrise.hentHorizontalKernel(3)

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
        val kernelMatrise:Matrix<Double> = Matrise.hentVertikalKernel(3)

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
