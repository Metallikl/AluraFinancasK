package ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dluche.R
import extention.getBrFormattedDate
import kotlinx.android.synthetic.main.transacao_item.view.*
import model.Transacao


class ListaTransacoesAdapter(private val transacoes: List<Transacao>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.transacao_item,parent,false)
        return TransacoesItem(viewItem)
    }

    override fun getItemCount(): Int {
       return transacoes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transacaoItem = holder as TransacoesItem
        transacaoItem.bindData(transacoes[position])
    }

    class TransacoesItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemV = itemView
        private val icone = itemV.transacao_icone
        private val categoria = itemV.transacao_categoria
        private val data = itemV.transacao_data
        private val valor = itemV.transacao_valor

        fun bindData(itemS : Transacao){
            categoria.text = itemS.categoria
            data.text =  itemS.data.getBrFormattedDate()
            valor.text = itemS.valor.toString()
        }
    }


}