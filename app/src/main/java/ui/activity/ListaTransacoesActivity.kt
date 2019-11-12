package ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dluche.R
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import model.Tipo
import model.Transacao
import ui.ResumoView
import ui.adapter.ListaTransacoesAdapter
import java.math.BigDecimal


class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        //
        val transacoes: List<Transacao> = transacoesDeExemplo()
        configuraResumo(transacoes)
        //
        configuraLista(transacoes)
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        // window.decorView retorna a view da Activity
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        with(lista_transacoes_listview) {
            layoutManager = LinearLayoutManager(
                this@ListaTransacoesActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ListaTransacoesAdapter(transacoes)
        }
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(100.0),
                categoria = "Almoço de final de semana",
                tipo = Tipo.DESPESA
                //Calendar.getInstance()

            ),
            Transacao(
                valor = BigDecimal(100.0),
                categoria = "Economia",
                tipo = Tipo.RECEITA
                //Calendar.getInstance(),
            ),
            Transacao(
                valor = BigDecimal(100.0),
                tipo = Tipo.DESPESA
            ),
            Transacao(
                valor = BigDecimal(200.0),
                categoria = "Prêmio",
                tipo = Tipo.RECEITA
            )

        )
    }
}