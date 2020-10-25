package com.dagdoni.millad.deeplearning

import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.*
import org.opencv.core.CvType.CV_32S

class Matrise() {

    private var mat:Mat

    constructor(rad: Int, kolonne: Int, verdi:Double):this(rad,kolonne){
        mat.put(0,0,verdi)
    }

    constructor(storrelse:Pair<Int,Int>):this(storrelse.first,storrelse.second)
    constructor(rad:Int,kolonne:Int):this(){
        mat = Mat.eye(rad,kolonne, CvType.CV_8UC1)
    }

    constructor(openCVMatrise: Mat):this(){
        mat = openCVMatrise
    }

    init {
        OpenCV.loadLocally()
        mat = Mat.eye(0,0, CvType.CV_8UC1)
    }

    fun trekk(matrise2: Matrise):Matrise{
        val dest:Mat = Mat()
        Core.absdiff(this.mat,matrise2.mat,dest)
        return Matrise(dest)
    }

    fun dot(matrise: Matrise):Double{
        return this.mat.dot(matrise.mat)
    }

    fun relu(x:Double):Double{
        if(x > 0.0) return x
        return 0.0
    }

    fun reluDerivant(x: Double): Double {
        if(x > 0.0) return 1.0
        return 0.0
    }

    fun reluDerivant(matrise: Matrise):Matrise{
        val storrelse = matrise.storrelse()
        (0 until storrelse.first).forEach {rad ->
            (0 until storrelse.second).forEach { kolonne ->
                val derivertReluVerdi = reluDerivant(matrise.mat.get(rad,kolonne).first())
                matrise.mat.put(rad,kolonne,derivertReluVerdi)
            }
        }
        return matrise
    }

    fun conv(kernelStorrelse: Int, storrelsePaConvOperasjon:Int):Matrise{
        val lag_0_input: Mat = Mat(storrelsePaConvOperasjon,storrelsePaConvOperasjon,CvType.CV_8UC1)
        (0 until storrelsePaConvOperasjon).forEach{i ->
            (0 until storrelsePaConvOperasjon).forEach{ j ->
                val lag_0:Matrise = this.hentVerdi(i,i+7,j,j+7)
                val lag_0_multiplisert_med_kernel = lag_0.multipliser(Matrise().hentVertikalKernel(kernelStorrelse,kernelStorrelse))
                val lag_1_sum_verdi = lag_0_multiplisert_med_kernel.sum()
                lag_0_input.put(i,j, *lag_1_sum_verdi)
            }
        }
        return Matrise(lag_0_input)
    }

    fun sum():DoubleArray{
        return Core.sumElems(mat).`val`
    }

    fun multipliser(matrise: Matrise):Matrise{
        return Matrise(mat.mul(matrise.hentMat()))
    }

    fun multipliser(scalarVerdi: Double):Matrise{
        return Matrise(mat.mul(mat,scalarVerdi))
    }

    fun hentMat():Mat{
        return mat;
    }

    fun tilfeldigeVekter():Matrise{
        if(!erTom()) Core.randu(mat,-10.0,10.0)
        return this
    }

    fun erTom():Boolean{
         return mat.size().empty()
    }

    fun hentForsteVerdi(rad:Int, kol:Int):Double{
        if(erTom()) return 0.0
        return mat.get(rad,kol).first()
    }

    fun hentVerdi(radStart:Int,radSlutt:Int, kolStart:Int, kolSlutt:Int): Matrise {
        return Matrise(mat.submat(radStart,radSlutt,kolStart,kolSlutt))
    }

    fun storrelse():Pair<Int,Int>{
        return Pair(mat.rows(),mat.cols())
    }

    fun somBitwiseNot(): Matrise {
        val endeligMatrise = Mat(mat.rows(),mat.cols(),mat.type())
        Core.bitwise_not(mat,endeligMatrise)
        val nyttBildeMatrise = Mat(endeligMatrise.rows(),endeligMatrise.cols(),endeligMatrise.type())
        Core.divide(255.0,endeligMatrise,nyttBildeMatrise)
        return Matrise(nyttBildeMatrise)
    }

    fun type():String{
        return CvType.typeToString(mat.type())
    }

    override fun toString():String{
        return mat.dump()
    }

    fun forstVerdi(): Int {
        return mat.get(0,0)?.get(0)?.toInt() ?: 0
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

    fun hentVertikalKernel(rader:Int, kolonner: Int): Matrise {
        return tilMatrise(rader, kolonner, hentVertikalKernal(rader,kolonner))
    }

    private fun tilMatrise(rader: Int, kolonner: Int, otherArray: Array<DoubleArray>): Matrise {
        val matObject = Mat.zeros(rader, kolonner, CvType.CV_8UC1)
        (0 until rader).forEach { kol ->
            (0 until kolonner).forEach { ra ->
                matObject.put(ra, kol, otherArray[ra][kol])
            }

        }
        return Matrise(matObject)
    }
}