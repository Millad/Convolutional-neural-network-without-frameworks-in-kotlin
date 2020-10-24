package com.dagdoni.millad.deeplearning

class App(val bilde: Bilde) {

    fun conv(matrise:Matrise, kernel: Matrise):ArrayList<DoubleArray>{
        val lag_0_input: ArrayList<DoubleArray> = ArrayList<DoubleArray>()
        (0 until 3).forEach{i ->
            (0 until 3).forEach{ j ->
                val lag_0:Matrise = matrise.hentVerdi(i,i+7,j,j+7)
                val lag_1 = lag_0.multipliser(kernel)
                val lag_1_sum = lag_1.sum()
                println(lag_1_sum.joinToString())
                lag_0_input.add(lag_1_sum)
            }
        }
        return lag_0_input
    }

    fun hentMatriseFraBildet(): Matrise {
        return bilde.somMatrise()
    }
}
fun main(args: Array<String>) {
    val app = App(Bilde("talletEn.png"))
    val matriseAvBilde: Matrise = app.hentMatriseFraBildet()
    val vertikalKernel:Matrise = Matrise().hentMatriseMedVertikalKernel(7,7)
    val vekter:Matrise = Matrise().tilfeldigeVekter()


    val lag_1 = app.conv(matriseAvBilde,vertikalKernel)

    val lag_2 = "relu"

    print(lag_1)

  //  println(matriseAvBilde)
}
