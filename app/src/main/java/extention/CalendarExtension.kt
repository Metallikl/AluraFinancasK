package extention

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getBrFormattedDate(): String{
    val formatoBr = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBr)
    return format.format(this.time)
}