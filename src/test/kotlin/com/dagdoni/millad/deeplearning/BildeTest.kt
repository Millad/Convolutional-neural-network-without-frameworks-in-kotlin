package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.opencv.core.CvType
import org.opencv.core.Mat

internal class BildeTest {

    @Test
    fun `gitt svart og hvit bilde, skal det returneres 1 (svart)  isteden for 255 og 0 (hvit) isteden for 1 som en bitwise_not operasjon`() {
        val mat:Mat = Mat.eye(1,1,CvType.CV_8UC1)
        assertThat(Bilde("").somBitwiseNot(mat).get(0,0).get(0)).isEqualTo(1.0)
    }


    @Test
    fun `skal kunne returnere tom Mat om filen ikke finnes`() {
        assertThat(Bilde("").somMatrise().elemSize()).isEqualTo(1L)
    }

    @Test
    fun `skal kunne init klassen`() {
        assertThat(Bilde("")).isNotNull
    }
}