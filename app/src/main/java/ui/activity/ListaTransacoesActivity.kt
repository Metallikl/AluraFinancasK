package ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dluche.R
import delegate.TransacaoDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import model.Transacao
import ui.ResumoView
import ui.adapter.ListaTransacoesAdapter
import ui.dialog.AdicionaTransacaoDialog


class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        //
        configuraResumo()
        //
        configuraLista()
        //
        //region AddReceita
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                AdicionaTransacaoDialog(
                    this,
                    window.decorView as ViewGroup
                )
                .configuraDialog(object : TransacaoDelegate{
                    override fun delegate(transacao: Transacao) {
                        atualizaTransacoes(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })

            }
        //endregion
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        // window.decorView retorna a view da Activity
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            layoutManager = LinearLayoutManager(
                this@ListaTransacoesActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ListaTransacoesAdapter(transacoes)
        }
    }
}