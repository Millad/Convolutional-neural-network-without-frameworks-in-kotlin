package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

class Matrise(val rad:Int,val kolonne:Int) {

    private var originalMatrise:Mat

    constructor(openCVMatrise: Mat):this(0,0){
        originalMatrise = openCVMatrise
    }

    init {
        OpenCV.loadLocally()
        originalMatrise = Mat.eye(rad,kolonne, CvType.CV_8UC1)
    }

    fun erTom():Boolean{
         return originalMatrise.size().empty()
    }

    fun somBitwiseNot(): Matrise {
        val endeligMatrise = Mat(originalMatrise.rows(),originalMatrise.cols(),originalMatrise.type())
        Core.bitwise_not(originalMatrise,endeligMatrise)
        val nyttBildeMatrise = Mat(endeligMatrise.rows(),endeligMatrise.cols(),endeligMatrise.type())
        Core.divide(255.0,endeligMatrise,nyttBildeMatrise)
        return Matrise(nyttBildeMatrise.rows(),nyttBildeMatrise.cols())
    }

    override fun toString():String{
        return originalMatrise.dump()
    }

    fun forstVerdi(): Int {
        return originalMatrise.get(0,0)?.get(0)?.toInt() ?: 0
    }

}