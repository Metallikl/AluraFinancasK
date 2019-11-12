package extention

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.getBrFormattedDecimal(): String {
    val formatoBr = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatoBr.format(this).replace("-R$","R$ -")
}