package com.dagdoni.millad.deeplearning

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class AppTest {

    @Test fun `skal kunne fa tilbake riktig matrise fra openCV Adapter`(){
        // GITT
        val bilde:Bilde = mockk<Bilde>("talletEn.png")
        val app = App(bilde)

        // NAAR
        every { bilde.somMatrise() } returns Matrise(3,3)
        val matrise: Matrise = app.hentMatriseFraBildet()

        //DA
        assertThat(matrise.erTom()).isFalse()
    }

    @Test fun `skal kunne returnere en tom matrise naar bildet er ikke funnet`() {
        // GITT
        val app = App(Bilde("feil navn"))

        // NAAR
        val matrise: Matrise = app.hentMatriseFraBildet()

        //DA
        assertThat(matrise.erTom()).isTrue()
    }

    @Test
    fun `skal kunne starte appen`() {
        assertThat(App(Bilde(""))).isNotNull
    }
}
