package delegate

import model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}