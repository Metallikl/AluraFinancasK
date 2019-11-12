package model

import java.math.BigDecimal

class Resumo(
        private val transacoes: List<Transacao>
    ) {

    val receita get() = somaPor(Tipo.RECEITA)

    val despesa get() = somaPor(Tipo.DESPESA)

    /*
    //Fun como Single expression function
    //fun total() : BigDecimal = receita().subtract(despesa())

    //Convertemos a função acima em um propertie da classe, assim ,
    //quando essa propertie for chamada o retorno dela será a execução fun subtract*/
    val total get() = receita.subtract(despesa)

    private fun somaPor(tipo: Tipo): BigDecimal {
       /* O codigo comentado é o modo JAVA
        var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }
        Mesmo codigo que o comentado acima, mas no modo KOTLIN
        Maneira KOTLIN de substituir o for com if para filtrar trasações pelo tipo
        val somaPorTipo = transacoes
            //trasacao -> é o alias para cada item da lista
            //filtra tracações que passarem no teste logico.
            .filter { transacao -> transacao.tipo == tipo }
            //sobre os itens filtrados, soma o valor
            .sumByDouble { transacao -> transacao.valor.toDouble() }*/
//        Por padrão, as funções que recebem expressões lambdas assumem o it como
//        apelido o item.
        val somaPorTipo = transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
        //
        return BigDecimal(somaPorTipo)
    }

}