package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import java.net.URL

class Bilde(val navnPaBilde: String) {

    init {
        OpenCV.loadLocally()
    }

    fun somMatrise(): Matrise {
        val url: URL = javaClass.classLoader.getResource(navnPaBilde) ?: return Matrise(0,0)
        val mat: Mat = Imgcodecs.imread(File(url.toURI()).toString(),CvType.CV_8UC1)
        //println(mat.size())
        //println(mat.dump())
        return Matrise(mat)
    }

    fun matrix(){
        val url: URL = javaClass.classLoader.getResource(navnPaBilde)
        val mat: Mat = Imgcodecs.imread(File(url.toURI()).toString(),CvType.CV_8UC1)
        //println(mat.size())
        //println(mat.dump())
        println(Matrise(mat).matrise().repr())

    }

}
