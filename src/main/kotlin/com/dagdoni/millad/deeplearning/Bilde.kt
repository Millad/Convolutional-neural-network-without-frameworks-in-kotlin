package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import java.net.URL

class Bilde(val navnPaBilde: String) {

    init {
        OpenCV.loadLocally()
    }

    fun somMatrise(): Matrise {
        val url: URL = javaClass.classLoader.getResource(navnPaBilde) ?: return Matrise(0,0)
        return Matrise(Imgcodecs.imread(File(url.toURI()).toString())).somBitwiseNot()
    }



}
