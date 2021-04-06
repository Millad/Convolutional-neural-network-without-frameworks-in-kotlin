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

    private var matrise: Matrix<Double>
    private var bildematrise: Mat

    constructor(mat:Mat):this(mat.rows(),mat.cols()){
        bildematrise = mat
        genererMatriseFrabildet()
    }

    constructor(rad: Int, kolonne: Int, verdi:Double):this(rad,kolonne){
        matrise = eye(rad,kolonne)
        matrise.set(0,0,verdi)
        bildematrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }
    constructor(kernelStorrelse: Int):this(kernelStorrelse,kernelStorrelse)
    constructor(rad:Int,kolonne:Int):this(){
        matrise = eye(rad,kolonne)
        bildematrise = Mat.eye(rad,kolonne,CvType.CV_8UC1)
    }

    constructor(matrix: Matrix<Double>):this(){
       this.matrise = matrix
    }

    init {
        OpenCV.loadLocally()
        matrise = eye(0)
        bildematrise = Mat.eye(0,0,CvType.CV_8UC1)
    }

    fun conv(kernel:Matrix<Double>):Matrix<Double>{
        val kernelStorrelse = kernel.numRows()
        val lag_0_bildematriser_fra_kernel:  ArrayList<Matrix<Double>>  = hentMatriserFraStridesOperasjon(kernelStorrelse)
        val lag_1_summeringer = eye(1,lag_0_bildematriser_fra_kernel.size)

        lag_0_bildematriser_fra_kernel.withIndex().forEach{ (index,bildematrise) ->
            val bildeDelProduktKernel = bildematrise *  kernel
            lag_1_summeringer.set(0,index,bildeDelProduktKernel.elementSum())
        }
        return lag_1_summeringer
    }

    fun hentMatriserFraStridesOperasjon(kernelStorrelse: Int):  ArrayList<Matrix<Double>> {
        val strideStorrelse = matrise.numCols() / kernelStorrelse
        val matrixList:ArrayList<Matrix<Double>> = ArrayList()
        for(strideNedoverBildet in 0 until  matrise.numRows() step strideStorrelse){
            for(strideHøyreForBildet in 0 until  matrise.numCols() step strideStorrelse){
                val enkelStrideMatrise = matrise.get(IntRange(strideNedoverBildet, strideNedoverBildet+1),IntRange(strideHøyreForBildet,strideHøyreForBildet+1))
                matrixList.add(enkelStrideMatrise)
            }
        }
        return matrixList
    }

    fun tilfeldigeVekter():Matrix<Double>{
        if(erTom()) return eye(storrelse().first,storrelse().second)
        return rand(1,storrelse().first, MatrixTypes.DoubleType)
    }

    fun erTom():Boolean{
         return this.matrise.to2DArray().isEmpty()
    }

    fun storrelse():Pair<Int,Int>{
        return Pair(matrise.numRows(),matrise.numCols())
    }

    private fun genererMatriseFrabildet() {
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

        this.matrise = mat
    }

    fun matrise():Matrix<Double>{
        return matrise
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