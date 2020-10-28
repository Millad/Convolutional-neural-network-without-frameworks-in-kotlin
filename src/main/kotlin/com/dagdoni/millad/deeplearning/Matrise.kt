package com.dagdoni.millad.deeplearning

import koma.extensions.get
import koma.extensions.set
import koma.eye
import nu.pattern.OpenCV
import org.opencv.core.CvType
import org.opencv.core.*
import koma.matrix.Matrix
import koma.matrix.MatrixTypes
import koma.rand

class Matrise() {

    private var matrix: Matrix<Double>
    private var bildeMatrise: Mat

    constructor(mat:Mat):this(mat.rows(),mat.cols()){
        bildeMatrise = mat
        genererMatrixFrabildet()
    }

    constructor(rad: Int, kolonne: Int, verdi:Double):this(rad,kolonne){
        matrix = eye(rad,kolonne)
        matrix.set(0,0,verdi)
        bildeMatrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }
    constructor(kernelStorrelse: Int):this(kernelStorrelse,kernelStorrelse)
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

    fun relu(x:Double):Double{
        if(x > 0.0) return x
        return 0.0
    }

    fun reluDerivant(x: Double): Double {
        if(x > 0.0) return 1.0
        return 0.0
    }

    fun reluDerivant(matrise: Matrix<Double>):Matrix<Double>{
        val kopi:Matrix<Double> = matrise.copy()
        (0 until kopi.numRows()).forEach {rad ->
            (0 until kopi.numCols()).forEach { kolonne ->
                val derivertReluVerdi = reluDerivant(matrise.get(rad,kolonne))
                kopi.set(rad,kolonne,derivertReluVerdi)
            }
        }
        return kopi
    }

    fun conv(kernelStorrelse: Int, kernel:Matrix<Double>):Matrix<Double>{
        val lag_0_input:Matrix<Double> = eye(kernelStorrelse,1)
        (0 until kernelStorrelse).forEach{i ->
            (0 until kernelStorrelse).forEach{ j ->
                val lag_0: Matrix<Double>  = this.hentMatriseVerdiFraBilde(i,j, kernelStorrelse)
                val lag_0_multiplisert_med_kernel =  kernel * lag_0
                val lag_1_sum_verdi = lag_0_multiplisert_med_kernel.elementSum()
                lag_0_input.set(i,0, lag_1_sum_verdi)
            }
        }
        return lag_0_input
    }

    fun hentMatriseVerdiFraBilde(rad:Int, kol:Int,kernelStorrelse: Int):  Matrix<Double>  {
        val mat:Matrix<Double> = eye(kernelStorrelse,kernelStorrelse)
        (rad until kernelStorrelse).forEach { i->
            (kol until kernelStorrelse).forEach { j->
                mat.set(rad, kol,matrise().get(i, j))
            }
        }
        return mat
    }

    fun tilfeldigeVekter():Matrix<Double>{
        if(erTom()) return eye(storrelse().first,storrelse().second)
        return rand(storrelse().first,1, MatrixTypes.DoubleType)
    }

    fun erTom():Boolean{
         return this.matrix.to2DArray().isEmpty()
    }

    fun hentForsteVerdi(rad:Int, kol:Int):Double{
        if(erTom()) return 0.0
        return matrix.get(rad,kol)
    }



    fun storrelse():Pair<Int,Int>{
        return Pair(matrix.numRows(),matrix.numCols())
    }

    fun genererMatrixFrabildet() {
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

        this.matrix = mat
    }


    fun matrise():Matrix<Double>{
        return matrix
    }

    override fun toString():String{
        return matrise().repr()
    }

    fun forstVerdi(): Double {
        if(erTom()) return 0.0
        return matrix.get(0,0)
    }

    private fun hentVertikalKernal(kernelStorrelse:Int): Array<DoubleArray> {
        val hovedKernel: ArrayList<DoubleArray> = ArrayList()
        repeat(kernelStorrelse) {
            val doubleArray = DoubleArray(kernelStorrelse)
            repeat(kernelStorrelse) {
                doubleArray[it] = 0.0
            }
            doubleArray[doubleArray.size / 2] = 1.0
            hovedKernel.add(doubleArray)
        }

        return hovedKernel.toTypedArray()
    }

    private fun hentHorizontalKernal(kernelStorrelse: Int): Array<DoubleArray> {
        val hovedKernel: ArrayList<DoubleArray> = ArrayList()
        repeat(kernelStorrelse) {
            val doubleArray = DoubleArray(kernelStorrelse)
            var radVerdi = 0.0
            if(it == (kernelStorrelse / 2)){
                radVerdi = 1.0
            }

            repeat(kernelStorrelse) {
                doubleArray[it] = radVerdi
            }
            hovedKernel.add(doubleArray)
        }

        return hovedKernel.toTypedArray()
    }

    fun hentHorizontalKernel(kernelStorrelse: Int):  Matrix<Double>  {
        return tilKernelMatrise(kernelStorrelse, hentHorizontalKernal(kernelStorrelse))
    }

    fun hentVertikalKernel(kernelStorrelse: Int):  Matrix<Double>  {
        return tilKernelMatrise(kernelStorrelse, hentVertikalKernal(kernelStorrelse))
    }

    private fun tilKernelMatrise(kernelStorrelse: Int, otherArray: Array<DoubleArray>): Matrix<Double> {
        val matObject = eye(kernelStorrelse
                , kernelStorrelse)
        (0 until kernelStorrelse).forEach { kol ->
            (0 until kernelStorrelse).forEach { ra ->
                matObject.set(ra, kol, otherArray[ra][kol])
            }

        }
        return matObject
    }

}