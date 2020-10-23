package com.dagdoni.millad.deeplearning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class AppTest {
    @Test
    fun `should init app`() {
        val classUnderTest = App()
        assertThat(classUnderTest).isNotNull()
    }
}
