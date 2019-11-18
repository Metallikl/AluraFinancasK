package ui.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dluche.R
import extention.getBrFormattedDate
import extention.getBrFormattedDecimal
import extention.limitaEmAte
import kotlinx.android.synthetic.main.transacao_item.view.*
import model.Tipo
import model.Transacao

private const val limiteDaCategoria = 14

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    //Recebe HoF ao inves da interface
    private val onItemClickListner: (position: Int, transacao: Transacao) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.transacao_item, parent, false)
        return TransacoesItem(viewItem)
    }

    override fun getItemCount(): Int {
        return transacoes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transacaoItem = holder as TransacoesItem
        transacaoItem.bindData(transacoes[position], onItemClickListner, this)
    }

    class TransacoesItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemV = itemView
        private val icone = itemV.transacao_icone
        private val categoria = itemV.transacao_categoria
        private val data = itemV.transacao_data
        private val valor = itemV.transacao_valor

        fun bindData(
            itemS: Transacao,
            onItemClickListner: (position: Int, transacao: Transacao) -> Unit,
            listaTransacoesAdapter: ListaTransacoesAdapter
        ) {
            defineClick(itemS, onItemClickListner)
            defineLongClick(itemS,listaTransacoesAdapter)
            defineData(itemS)
            defineValor(itemS)
            defineCategoria(itemS)
            //
            defineValorTextColor(itemS.tipo)
            defineIcon(itemS.tipo)
        }



        private fun defineLongClick(
            itemS: Transacao,
            listaTransacoesAdapter: ListaTransacoesAdapter
        ) {
            //
            itemV.apply {
                setOnCreateContextMenuListener { menu, _, _ ->
                    menu.add(
                        Menu.NONE,//Id do Grupo de menu. No caso não importe usamos o none
                        1,//Id do item do menu,
                        Menu.NONE,//Ordenação do item, como só teremos 1 item, usaremos none
                        "Remover"//Titulo do menu
                    )
                    listaTransacoesAdapter.mPosition = adapterPosition
                }
//                setOnLongClickListener{
//                    listaTransacoesAdapter.mPosition = adapterPosition
//                    true
//                }
            }
            //

        }

        private fun defineClick(
            itemS: Transacao,
            onItemClickListner: (position: Int, transacao: Transacao) -> Unit
        ) {
            itemV.setOnClickListener(View.OnClickListener {
                onItemClickListner(adapterPosition, itemS)
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
                when (tipo) {
                    Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
                    else -> R.drawable.icone_transacao_item_despesa
                }
            //
            icone.setBackgroundResource(icon)
        }

        fun defineValorTextColor(tipo: Tipo) {
            val cor: Int = when (tipo) {
                Tipo.RECEITA -> R.color.receita
                else -> R.color.despesa
            }
            //
            valor.setTextColor(ContextCompat.getColor(itemV.context, cor))
        }
    }
}