package ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dluche.R
import delegate.TransacaoClickDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import model.Tipo
import model.Transacao
import ui.ResumoView
import ui.adapter.ListaTransacoesAdapter
import ui.dialog.AdicionaTransacaoDialog
import ui.dialog.AlteraTransacaoDialog


class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    //Inicialização "preguiçosa" significa que a inicialização será feita
    //somente no primeiro momento em que propertie for ser utilizada.
    //Ou seja, essa incialização será feita no primeiro momento em que a var viewAct for ser usada.
    //Após a primeira chamada, o valo retornado no lazy torna-se o valor imutavel da propertie
    private val viewAct by lazy {
        window.decorView
    }
    //Properties que utilizem properties inicializadas by lazy, também tem que ter sua inicialização by lazy
    private val viewGroupAct by lazy {
        viewAct as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        //
        //viewAct = window.decorView
        //
        configuraResumo()
        //
        configuraLista()
        //
        configurFab()
        //
    }

    private fun configurFab() {
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.RECEITA)

            }
        //
        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.DESPESA)
            }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(
            this,
            viewGroupAct
        ).chama(tipo) { transacaoCriada ->
                adiciona(transacaoCriada)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        // viewAct retorna a view da Activity
        val resumoView = ResumoView(this, viewAct, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            layoutManager = LinearLayoutManager(
                this@ListaTransacoesActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ListaTransacoesAdapter(
                transacoes,
                object : TransacaoClickDelegate {
                    override fun onItemClick(position: Int, transacao: Transacao) {
                        chamaDialogDeAlteracao(transacao, position)
                    }
                })

        }
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(this@ListaTransacoesActivity, viewAct as ViewGroup)
            .chama(
                transacao
            ) { transacaoAlterada ->
                altera(transacaoAlterada, position)
            }
    }

    private fun altera(transacao: Transacao, position: Int) {
        transacoes[position] = transacao
        atualizaTransacoes()
    }
}