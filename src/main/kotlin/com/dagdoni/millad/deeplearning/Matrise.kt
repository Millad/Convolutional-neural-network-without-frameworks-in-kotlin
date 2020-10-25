package com.dagdoni.millad.deeplearning

import koma.extensions.emul
import koma.extensions.get
import koma.extensions.set
import koma.eye
import koma.mat
import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.*
import koma.matrix.Matrix
import koma.matrix.MatrixType
import koma.matrix.MatrixTypes
import koma.rand
import kotlin.random.Random

class Matrise() {

    private var matrix: Matrix<Double>
    private var bildeMatrise: Mat

    constructor(mat:Mat):this(mat.rows(),mat.cols()){
        bildeMatrise = mat
    }

    constructor(rad: Int, kolonne: Int, verdi:Double):this(rad,kolonne){
        matrix = eye(rad,kolonne)
        matrix.set(0,0,verdi)
        bildeMatrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }
    constructor(storrelse:Pair<Int,Int>):this(storrelse.first,storrelse.second)
    constructor(rad:Int,kolonne:Int):this(){
        matrix = eye(rad,kolonne)
        bildeMatrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }


    constructor(matrix: Matrix<Double>):this(){
       this.matrix = matrix
    }

    init {
        OpenCV.loadLocally()
        matrix = eye(0)
        bildeMatrise = Mat.eye(0,0,CvType.CV_8UC1)
    }

    fun trekk(matrise2: Matrise):Matrise{
        return Matrise(this.matrix.minus(matrise2.matrix))
    }

    fun dot(matrise: Matrise):Double{
        return (this.matrix emul matrix).get(0)
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
        val kopi:Matrix<Double> = matrise.matrix
        val storrelse = matrise.storrelse()
        (0 until storrelse.first).forEach {rad ->
            (0 until storrelse.second).forEach { kolonne ->
                val derivertReluVerdi = reluDerivant(matrise.matrix.get(rad,kolonne))
                kopi.set(rad,kolonne,derivertReluVerdi)
            }
        }
        return Matrise(kopi)
    }

    fun conv(kernelStorrelse: Int, storrelsePaConvOperasjon:Int):Matrise{
        val lag_0_input:Matrix<Double> = eye(storrelsePaConvOperasjon,storrelsePaConvOperasjon)
        (0 until storrelsePaConvOperasjon).forEach{i ->
            (0 until storrelsePaConvOperasjon).forEach{ j ->
                val lag_0:Matrise = this.hentVerdi(i,i+7,j,j+7)
                val lag_0_multiplisert_med_kernel = lag_0.multipliser(Matrise().hentVertikalKernel(kernelStorrelse,kernelStorrelse))
                val lag_1_sum_verdi = lag_0_multiplisert_med_kernel.sum()
                lag_0_input.set(i,j, lag_1_sum_verdi)
            }
        }
        return Matrise(lag_0_input)
    }

    fun sum(): Double {
        return matrix.elementSum()
    }

    fun multipliser(matrise: Matrise):Matrise{
        return Matrise(this.matrix * matrix)
    }

    fun multipliser(scalarVerdi: Double):Matrise{
        return Matrise(this.matrix * (this.matrix * scalarVerdi))
    }

    fun hentMat():Matrix<Double>{
        return matrix;
    }

    fun tilfeldigeVekter():Matrise{
        if(erTom()) return this
        return Matrise(rand(storrelse().first,storrelse().second, MatrixTypes.DoubleType)*((Math.random() * 00.1) + 0.001))
    }

    fun erTom():Boolean{
         return this.matrix.to2DArray().isEmpty()
    }

    fun hentForsteVerdi(rad:Int, kol:Int):Double{
        if(erTom()) return 0.0
        return matrix.get(rad,kol)
    }

    fun hentVerdi(radStart:Int,radSlutt:Int, kolStart:Int, kolSlutt:Int): Matrise {
        val radRange = IntRange(radStart,radSlutt-1)
        val kolonneRange = IntRange(kolStart,kolSlutt-1)
        return Matrise(matrix.get(radRange,kolonneRange))
    }

    fun storrelse():Pair<Int,Int>{
        return Pair(matrix.numRows(),matrix.numCols())
    }

    fun somBitwiseNot(): Matrise {
        val endeligMatrise = Mat(bildeMatrise.rows(),bildeMatrise.cols(),bildeMatrise.type())
        Core.bitwise_not(bildeMatrise,endeligMatrise)
        val nyttBildeMatrise = Mat(endeligMatrise.rows(),endeligMatrise.cols(),endeligMatrise.type())
        Core.divide(255.0,endeligMatrise,nyttBildeMatrise)

        val mat:Matrix<Double> = eye(nyttBildeMatrise.rows(),nyttBildeMatrise.cols())

        (0 until nyttBildeMatrise.rows()).forEach {rad ->
            (0 until nyttBildeMatrise.cols()).forEach { kolonne ->
                mat.set(rad,kolonne,nyttBildeMatrise.get(rad,kolonne).first())
            }
        }

        return Matrise(mat)
    }

    fun toMatrix():Matrise{
        val mat:Matrix<Double> = eye(bildeMatrise.rows(),bildeMatrise.cols())

        (0 until bildeMatrise.rows()).forEach {rad ->
            (0 until bildeMatrise.cols()).forEach { kolonne ->
                mat.set(rad,kolonne,bildeMatrise.get(rad,kolonne).first())
            }
        }
        return Matrise(mat);
    }

    override fun toString():String{
        return matrix.repr()
    }

    fun forstVerdi(): Double {
        if(erTom()) return 0.0
        return matrix.get(0,0)
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
        val matObject = eye(rader, kolonner)
        (0 until rader).forEach { kol ->
            (0 until kolonner).forEach { ra ->
                matObject.set(ra, kol, otherArray[ra][kol])
            }

        }
        return Matrise(matObject)
    }
}