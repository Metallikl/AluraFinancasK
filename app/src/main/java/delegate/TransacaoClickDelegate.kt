package delegate

import model.Transacao

interface TransacaoClickDelegate {
    fun onItemClick(position:Int, transacao: Transacao)
}