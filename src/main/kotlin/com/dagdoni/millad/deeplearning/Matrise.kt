package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.CvType
import org.opencv.core.Mat

class Matrise(val rad:Int,val kolonne:Int) {

    private val mat:Mat

    init {
        OpenCV.loadLocally()
        mat = Mat.eye(1,1, CvType.CV_8UC1)
    }

    fun erTom():Boolean{
         return mat.size().empty()
    }

}