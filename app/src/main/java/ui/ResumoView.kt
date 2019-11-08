package ui

import android.view.View
import extention.getBrFormattedDecimal
import kotlinx.android.synthetic.main.resumo_card.view.*
import model.Tipo
import model.Transacao
import java.math.BigDecimal

class ResumoView(private val view: View,
                 private val transacoes:  List<Transacao>) {

    fun adicionaReceita() {
        var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }
        //
        view.resumo_card_receita.text = totalReceita.getBrFormattedDecimal()
    }

    fun adicionaDespesa() {
        var totalDespesa = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.DESPESA) {
                totalDespesa = totalDespesa.plus(transacao.valor)
            }
        }
        //
        view.resumo_card_despesa.text = totalDespesa.getBrFormattedDecimal()
    }
}