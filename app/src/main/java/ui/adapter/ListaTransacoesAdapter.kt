package ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dluche.R
import delegate.TransacaoClickDelegate
import extention.getBrFormattedDate
import extention.getBrFormattedDecimal
import extention.limitaEmAte
import kotlinx.android.synthetic.main.transacao_item.view.*
import model.Tipo
import model.Transacao

private const val limiteDaCategoria = 14

class ListaTransacoesAdapter(private val transacoes: List<Transacao>, private val onItemClickListner: TransacaoClickDelegate) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.transacao_item,parent,false)
        return TransacoesItem(viewItem)
    }

    override fun getItemCount(): Int {
       return transacoes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transacaoItem = holder as TransacoesItem
        transacaoItem.bindData(transacoes[position], onItemClickListner)
    }

    class TransacoesItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemV = itemView
        private val icone = itemV.transacao_icone
        private val categoria = itemV.transacao_categoria
        private val data = itemV.transacao_data
        private val valor = itemV.transacao_valor

        fun bindData(itemS: Transacao, onItemClickListner: TransacaoClickDelegate){
            defineClick(itemS,onItemClickListner)
            defineData(itemS)
            defineValor(itemS)
            defineCategoria(itemS)
            //
            defineValorTextColor(itemS.tipo)
            defineIcon(itemS.tipo)
        }

        private fun defineClick(itemS: Transacao,onItemClickListner: TransacaoClickDelegate) {
            itemV.setOnClickListener(View.OnClickListener {
                onItemClickListner.onItemClick(adapterPosition, itemS)
            })
        }

        private fun defineCategoria(itemS: Transacao) {
            categoria.text = itemS.categoria.limitaEmAte(limiteDaCategoria)
        }

        private fun defineValor(itemS: Transacao) {
            valor.text = itemS.valor.getBrFormattedDecimal()
        }

        private fun defineData(itemS: Transacao) {
            data.text = itemS.data.getBrFormattedDate()
        }

        private fun defineIcon(tipo: Tipo) {
            val icon: Int =
            when(tipo){
                Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
                else -> R.drawable.icone_transacao_item_despesa
            }
            //
            icone.setBackgroundResource(icon)
        }

        fun defineValorTextColor(tipo: Tipo){
            val cor : Int = when(tipo){
                            Tipo.RECEITA -> R.color.receita
                            else -> R.color.despesa
                        }
            //
            valor.setTextColor(ContextCompat.getColor(itemV.context,cor))
        }
    }
}