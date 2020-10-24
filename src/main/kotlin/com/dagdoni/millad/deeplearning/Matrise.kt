package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.*

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


    fun tilfeldigeVekter(){
        Core.randu(originalMatrise,-500.0,500.0)
    }

    fun erTom():Boolean{
         return originalMatrise.size().empty()
    }


    fun hentVerdi(rad:Int,kol:Int):Double{
        return originalMatrise.get(rad,kol).first()
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

    private fun hentVertikalKernal(antallRader:Int, antallKolonner: Int): Array<DoubleArray> {
        val hovedKernel: ArrayList<DoubleArray> = ArrayList()
        repeat(antallRader) {
            val doubleArray = DoubleArray(antallKolonner)
            repeat(antallKolonner) {
                doubleArray[it] = 0.0
            }
            doubleArray[doubleArray.size / 2] = 1.0
            hovedKernel.add(doubleArray)
        }

        return hovedKernel.toTypedArray()
    }

    private fun hentHorizontalKernal(antallRader:Int, antallKolonner: Int): Array<DoubleArray> {
        val hovedKernel: ArrayList<DoubleArray> = ArrayList()
        repeat(antallRader) {
            val doubleArray = DoubleArray(antallKolonner)
            var radVerdi = 0.0
            if(it == (antallKolonner / 2)){
                radVerdi = 1.0
            }

            repeat(antallKolonner) {
                doubleArray[it] = radVerdi
            }
            hovedKernel.add(doubleArray)
        }

        return hovedKernel.toTypedArray()
    }

    fun hentMatriseMedHorizontalKernel(rader:Int, kolonner: Int): Matrise {
        return tilMatrise(rader, kolonner, hentHorizontalKernal(rader,kolonner))
    }

    fun hentMatriseMedVertikalKernel(rader:Int, kolonner: Int): Matrise {
        return tilMatrise(rader, kolonner, hentVertikalKernal(rader,kolonner))
    }

    private fun tilMatrise(rader: Int, kolonner: Int, otherArray: Array<DoubleArray>): Matrise {
        val matObject = Mat.zeros(rader, kolonner, CvType.CV_64FC1)
        (0 until rader).forEach { kol ->
            (0 until kolonner).forEach { ra ->
                matObject.put(ra, kol, otherArray[ra][kol])
            }

        }
        println(matObject.dump())
        return Matrise(matObject)
    }

}