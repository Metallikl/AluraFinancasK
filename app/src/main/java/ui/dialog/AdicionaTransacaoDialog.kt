package ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dluche.R
import delegate.TransacaoDelegate
import extention.converteParaCalendar
import extention.getBrFormattedDate
import kotlinx.android.synthetic.main.form_transacao.view.*
import model.Tipo
import model.Transacao
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup) {

    val dialogView = criaLayout()

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria()
        configuraForm(transacaoDelegate)
    }

    private fun configuraForm(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.adiciona_receita)
            setView(dialogView)
            setPositiveButton(
                "Adicionar"
            ) { _, _ ->
                val valorEmTexto = dialogView.form_transacao_valor.text.toString()
                val dataEmTexto = dialogView.form_transacao_data.text.toString()
                val categoriaEmTexto = dialogView.form_transacao_categoria.selectedItem.toString()
                //
                val valor = convertCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = Tipo.RECEITA,
                    valor = valor,
                    categoria = categoriaEmTexto,
                    data = data
                )
                //
                transacaoDelegate.delegate(transacaoCriada)
            }
            setNegativeButton("Cancelar ", null)
        }.show()
    }

    private fun convertCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                context,
                "Falha na conversão do valor",
                Toast.LENGTH_LONG
            ).show()
            //
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria() {
        val mAdapter =
            ArrayAdapter
                .createFromResource(
                    context,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item
                )
        //
        dialogView.form_transacao_categoria.apply {
            adapter = mAdapter
        }
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        //
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        dialogView.form_transacao_data.apply {
            setText(hoje.getBrFormattedDate())
            //
            setOnClickListener {
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        form_transacao_data.setText(dataSelecionada.getBrFormattedDate())
                    },
                    ano,
                    mes,
                    dia
                ).show()
            }
        }
    }

    private fun criaLayout(): View {
        //Infla layout do dialog
        return LayoutInflater
            .from(context)
            .inflate(
                R.layout.form_transacao,
                viewGroup,
                false
            )
    }
}