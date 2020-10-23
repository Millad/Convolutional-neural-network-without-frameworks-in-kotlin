package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.opencv.core.CvType
import org.opencv.core.Mat

internal class OpenCVAdapterTest {

    @Test
    fun `gitt svart og hvit bilde, skal det returneres 255 (hvit)  isteden for 0 og 1 isteden for 0 (svart) som en bitwise_not operasjon`() {
        val mat:Mat = Mat.eye(1,1,CvType.CV_8UC1	)
        assertThat(OpenCVAdapter().utforEnMatriseBitwiseNot(mat).get(0,0).get(0)).isEqualTo(254.0)
    }


    @Test
    fun `skal kunne returnere tom Mat om filen ikke finnes`() {
        assertThat(OpenCVAdapter().lesBildetSomMatrise("").elemSize()).isEqualTo(0)
    }

    @Test
    fun `skal kunne init klassen`() {
        assertThat(OpenCVAdapter()).isNotNull
    }
}