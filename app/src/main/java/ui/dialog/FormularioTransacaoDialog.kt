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
import extention.converteParaCalendar
import extention.getBrFormattedDate
import kotlinx.android.synthetic.main.form_transacao.view.*
import model.Tipo
import model.Transacao
import java.math.BigDecimal
import java.util.*
//Toda classe abstrata é open por padrão
abstract class FormularioTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {
    private val dialogView = criaLayout()
    protected val campoData = dialogView.form_transacao_data
    protected val campoValor = dialogView.form_transacao_valor
    protected val campoCategoria = dialogView.form_transacao_categoria
    protected abstract val tituloBotaoPositivo : String

    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraForm(tipo, delegate)
    }

    private fun configuraForm(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPorTipo(tipo)
        //
        AlertDialog.Builder(context).apply {
            setTitle(titulo)
            setView(dialogView)
            setPositiveButton(
                tituloBotaoPositivo
            ) { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()
                //
                val valor = convertCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    categoria = categoriaEmTexto,
                    data = data
                )
                //
                delegate(transacaoCriada)
            }
            setNegativeButton("Cancelar ", null)
        }.show()
    }

    protected abstract fun tituloPorTipo(tipo: Tipo): Int

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

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPorTipo(tipo)

        val mAdapter =
            ArrayAdapter
                .createFromResource(
                    context,
                    categorias,
                    android.R.layout.simple_spinner_dropdown_item
                )
        //
        campoCategoria.apply {
            adapter = mAdapter
        }
    }

    protected fun categoriaPorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        //
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.apply {
            setText(hoje.getBrFormattedDate())
            //
            setOnClickListener {
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        campoData.setText(dataSelecionada.getBrFormattedDate())
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