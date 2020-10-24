package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BildeTest {

    @Test
    fun `skal kunne returnere tom Mat om filen ikke finnes`() {
        assertThat(Bilde("").somMatrise().erTom()).isTrue()
    }

    @Test
    fun `skal kunne init klassen`() {
        assertThat(Bilde("")).isNotNull
    }
}