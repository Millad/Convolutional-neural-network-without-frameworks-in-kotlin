package com.dagdoni.millad.deeplearning

import io.mockk.every
import io.mockk.mockk
import nu.pattern.OpenCV
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.opencv.core.CvType
import org.opencv.core.Mat


class AppTest {

    @BeforeEach
    fun setup(){
        OpenCV.loadLocally()
    }

    @Test fun `skal kunne fa tilbake riktig matrise fra openCV Adapter`(){
        // GITT
        val openCVAdapter:OpenCVAdapter = mockk<OpenCVAdapter>()
        val app:App = App(openCVAdapter)

        // NAAR
        every { openCVAdapter.lesBildetSomMatrise(any()) } returns Mat.eye(3,3,CvType.CV_8UC1)
        val matrise: Mat = app.hentMatriseFraBildet("talletEn.png")

        //DA
        assertThat(matrise.dump()).isEqualTo(Mat.eye(3,3,CvType.CV_8UC1).dump())
    }

    @Test fun `skal kunne returnere en tom matrise naar bildet er ikke funnet`() {
        // GITT
        val app:App = App(OpenCVAdapter())

        // NAAR
        val matrise: Mat = app.hentMatriseFraBildet("ingenting")

        //DA
        assertThat(matrise.elemSize()).isEqualTo(0)
    }

    @Test
    fun `skal kunne starte appen`() {
        assertThat(App(OpenCVAdapter())).isNotNull
    }
}
