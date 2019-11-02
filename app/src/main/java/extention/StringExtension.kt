package extention

fun String.limitaEmAte(caracters: Int) : String{
    if(this.length > caracters){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter,caracters)} ..."
    }
    //
    return this
}