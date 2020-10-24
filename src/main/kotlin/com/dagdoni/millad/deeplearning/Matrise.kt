package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

class Matrise() {

    private var originalMatrise:Mat

    constructor(rad:Int,kolonne:Int):this(){
        originalMatrise = Mat.eye(rad,kolonne, CvType.CV_8UC1)
    }
    constructor(openCVMatrise: Mat):this(){
        originalMatrise = openCVMatrise
    }

    init {
        OpenCV.loadLocally()
        originalMatrise = Mat.eye(0,0, CvType.CV_8UC1)
    }

    fun erTom():Boolean{
         return originalMatrise.size().empty()
    }


    fun storrelse():Pair<Int,Int>{
        return Pair(originalMatrise.rows(),originalMatrise.cols())
    }

    fun somBitwiseNot(): Matrise {
        val endeligMatrise = Mat(originalMatrise.rows(),originalMatrise.cols(),originalMatrise.type())
        Core.bitwise_not(originalMatrise,endeligMatrise)
        val nyttBildeMatrise = Mat(endeligMatrise.rows(),endeligMatrise.cols(),endeligMatrise.type())
        Core.divide(255.0,endeligMatrise,nyttBildeMatrise)
        return Matrise(nyttBildeMatrise)
    }

    fun type():String{
        return CvType.typeToString(originalMatrise.type())
    }
    override fun toString():String{
        return originalMatrise.dump()
    }

    fun forstVerdi(): Int {
        return originalMatrise.get(0,0)?.get(0)?.toInt() ?: 0
    }

    fun henVertiaklKernalSomArrayAvArrays(antallRader:Int, antallKolonner: Int): Array<DoubleArray> {
        val hovedKernel: ArrayList<DoubleArray> = ArrayList()
        repeat(antallRader) {
            val doubleArray = DoubleArray(antallKolonner)
            repeat(antallKolonner) {
                doubleArray[it] = 0.0
            }
            hovedKernel.add(doubleArray)
        }

        return hovedKernel.toTypedArray()
    }
    fun lagKernelVertikalLinje(): Matrise {
       val rader:Int = originalMatrise.rows()
        val kolonner:Int = originalMatrise.cols()
        val otherArray:Array<DoubleArray> = henVertiaklKernalSomArrayAvArrays(rader,kolonner)
        val matObject = Mat.zeros(rader, kolonner, CvType.CV_64FC1)
        (0 until rader).forEach { kol ->
            (0 until kolonner).forEach {ra ->
                matObject.put(ra, kol, otherArray[ra][kol])
            }

        }
        println(matObject.dump())
        return Matrise(matObject)
    }

}