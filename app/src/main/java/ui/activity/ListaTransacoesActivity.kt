package ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dluche.R
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import model.Transacao
import ui.adapter.ListaTransacoesAdapter
import java.math.BigDecimal
import java.util.*


class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)


        val transacoes = listOf(
                            Transacao(
                                BigDecimal(20.5),
                                "Comida",
                                Calendar.getInstance()
                            ),
                            Transacao(
                                BigDecimal(100.0),
                                "Economia",
                                Calendar.getInstance()
                            )
            )
        //
        with(lista_transacoes_listview){
            layoutManager = LinearLayoutManager(this@ListaTransacoesActivity, LinearLayoutManager.VERTICAL,false)
            adapter = ListaTransacoesAdapter(transacoes)
        }
    }
}