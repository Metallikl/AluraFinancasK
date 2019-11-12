package ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.dluche.R.color.despesa
import com.dluche.R.color.receita
import extention.getBrFormattedDecimal
import kotlinx.android.synthetic.main.resumo_card.view.*
import model.Resumo
import model.Transacao
import java.math.BigDecimal

class ResumoView(private val context : Context,
                 private val view: View,
                 transacoes:  List<Transacao>) {

    private  val resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, receita)
    private val corDespesa = ContextCompat.getColor(context, despesa)

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        view.resumo_card_receita.apply {
            text = totalReceita.getBrFormattedDecimal()
            setTextColor(corReceita)
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        view.resumo_card_despesa.apply {
            text = totalDespesa.getBrFormattedDecimal()
            setTextColor(corDespesa)
        }
    }

    private fun adicionaTotal(){
        val total = resumo.total
        val cor = corTotal(total)
        //
        view.resumo_card_total.apply {
            text = total.getBrFormattedDecimal()
            setTextColor(ContextCompat.getColor(context,cor))
        }
    }

    fun corTotal(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
           return receita
        }
        //
        return despesa

    }
}