package com.dagdoni.millad.deeplearning

import koma.extensions.get
import koma.extensions.set
import koma.eye
import koma.matrix.Matrix
import koma.matrix.MatrixTypes
import koma.rand
import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

class Matrise() {

    private var matrix: Matrix<Double>
    private var bildematrise: Mat

    constructor(mat:Mat):this(mat.rows(),mat.cols()){
        bildematrise = mat
        genererMatrixFrabildet()
    }

    constructor(rad: Int, kolonne: Int, verdi:Double):this(rad,kolonne){
        matrix = eye(rad,kolonne)
        matrix.set(0,0,verdi)
        bildematrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }
    constructor(kernelStorrelse: Int):this(kernelStorrelse,kernelStorrelse)
    constructor(rad:Int,kolonne:Int):this(){
        matrix = eye(rad,kolonne)
        bildematrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }

    constructor(matrix: Matrix<Double>):this(){
       this.matrix = matrix
    }

    init {
        OpenCV.loadLocally()
        matrix = eye(0)
        bildematrise = Mat.eye(0,0,CvType.CV_8UC1)
    }

    fun conv(kernel:Matrix<Double>):Matrix<Double>{
        val kernelStorrelse = kernel.numRows()
        val lag_0_bilde_split_kernel_storrelser:  ArrayList<Matrix<Double>>  = hentMatriseStriveForKernel(kernelStorrelse)
        val lag_2_summeringer = eye(1,lag_0_bilde_split_kernel_storrelser.size)

        lag_0_bilde_split_kernel_storrelser.withIndex().forEach{(index,bildeMatrise) ->
            val bildeDelProduktKernel = bildeMatrise *  kernel
            lag_2_summeringer.set(0,index,bildeDelProduktKernel.elementSum())
        }
        return lag_2_summeringer
    }

    fun hentMatriseStriveForKernel(kernelStorrelse: Int):  ArrayList<Matrix<Double>> {
        val strideStorrelse = matrix.numCols() / kernelStorrelse
        val matrixList:ArrayList<Matrix<Double>> = ArrayList()
        for(strideDown in 0 until  matrix.numRows() step strideStorrelse){
            for(strideRight in 0 until  matrix.numCols() step strideStorrelse){
                val m = matrix.get(IntRange(strideDown, strideDown+1),IntRange(strideRight,strideRight+1))
                matrixList.add(m)
            }
        }
        return matrixList
    }

    fun tilfeldigeVekter():Matrix<Double>{
        if(erTom()) return eye(storrelse().first,storrelse().second)
        return rand(1,storrelse().first, MatrixTypes.DoubleType)
    }

    fun erTom():Boolean{
         return this.matrix.to2DArray().isEmpty()
    }

    fun storrelse():Pair<Int,Int>{
        return Pair(matrix.numRows(),matrix.numCols())
    }

    private fun genererMatrixFrabildet() {
        val endeligMatrise = Mat(bildematrise.rows(),bildematrise.cols(),bildematrise.type())
        Core.bitwise_not(bildematrise,endeligMatrise)
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

    companion object Matrise{
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
}