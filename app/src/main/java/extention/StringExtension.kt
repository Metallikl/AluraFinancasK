package extention

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracters: Int) : String{
    if(this.length > caracters){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter,caracters)} ..."
    }
    //
    return this
}

fun String.converteParaCalendar(): Calendar {
    //formata o string data para date
    val formatoBr = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBr.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}