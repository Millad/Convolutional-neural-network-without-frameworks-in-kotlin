package com.dagdoni.millad.deeplearning

import org.opencv.core.Core;
import nu.pattern.OpenCV
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import java.net.URL

class OpenCVAdapter() {

    init {
        OpenCV.loadLocally()
    }

    fun lesBildetSomMatrise(navnPaBilde:String): Mat {
        val url: URL = javaClass.classLoader.getResource(navnPaBilde) ?: return Mat()
        return utforEnMatriseBitwiseNot(Imgcodecs.imread(File(url.toURI()).toString()))
    }

    fun utforEnMatriseBitwiseNot(originalMatrise: Mat): Mat {
        val endeligMatrise = Mat(originalMatrise.rows(),originalMatrise.cols(),originalMatrise.type())
        Core.bitwise_not(originalMatrise,endeligMatrise)
        val nyttBildeMatrise = Mat(endeligMatrise.rows(),endeligMatrise.cols(),endeligMatrise.type())
        Core.divide(255.0,endeligMatrise,nyttBildeMatrise)
        return nyttBildeMatrise
    }

}
