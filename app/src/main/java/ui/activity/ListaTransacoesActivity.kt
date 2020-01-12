package ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dluche.R
import dao.TransacaoDao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import model.Tipo
import model.Transacao
import ui.ResumoView
import ui.adapter.ListaTransacoesAdapter
import ui.dialog.AdicionaTransacaoDialog
import ui.dialog.AlteraTransacaoDialog
import java.math.BigDecimal


class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDao()
    private val transacoes = dao.transacoes
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
        dao.adiciona(transacao)
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
                transacoes
            )
            //Transformado delgate em HoF
            { position, transacao ->
                chamaDialogDeAlteracao(transacao, position)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idDoMenu = item.itemId
        //
        if (idDoMenu == 1) {
            val adapater = lista_transacoes_listview.adapter as ListaTransacoesAdapter
            val posicaoDaTransacao = adapater.mPosition
            remove(posicaoDaTransacao)
        }
        //
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
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
        dao.altera(transacao,position)
        atualizaTransacoes()
    }
}