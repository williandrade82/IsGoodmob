package br.com.fiap.isgood.model

import br.com.fiap.isgood.model.dao.LancheDAO
import br.com.fiap.isgood.model.dao.UsuarioAvaliadorDAO

data class AvaliacaoProduto (
    val id:Int,
    val usuarioAvaliador: UsuarioAvaliador,
    val lanche:Lanche,
    val comentario:String,
    val nota:Float
)  {

    companion object {
        fun getSampleArray() : ArrayList<AvaliacaoProduto>{
            val avaliadores = UsuarioAvaliadorDAO.getByFilter()
            val lanches = LancheDAO.getSampleData()
            val ret = ArrayList<AvaliacaoProduto>()

            ret.add(
                AvaliacaoProduto(
                    1,
                    avaliadores[0],
                    lanches[0],
                    "Lanche muito bom",
                    4.5F
                ))

            ret.add(
                AvaliacaoProduto(
                    2,
                    avaliadores[0],
                    lanches[1],
                    "Gostoso, mas pode melhorar muito. Justo pelo preço.",
                    3.5F
                ))

            ret.add(
                AvaliacaoProduto(
                    3,
                    avaliadores[0],
                    lanches[2], // Monstro
                    "Pelo nome, achei que era maior. Não parece nada com o da foto.",
                    3.5F
                ))

            ret.add(
                AvaliacaoProduto(4,
                avaliadores[1],
                lanches[0],
                "Melhor lanche da vidaaa.",
                5F)
            )

            ret.add(
                AvaliacaoProduto(5,
                    avaliadores[1],
                    lanches[1],
                    "Maravilhoso!",
                    5F)
            )
            ret.add(
                AvaliacaoProduto(6,
                    avaliadores[1],
                    lanches[2],
                    "Monstruosamente incrível!",
                    5F)
            )
            ret.add(
                AvaliacaoProduto(7,
                    avaliadores[2],
                    lanches[0],
                    "Perca de tempo... não comprem...",
                    1F)
            )
            ret.add(
                AvaliacaoProduto(8,
                    avaliadores[2],
                    lanches[1],
                    "Pior lanche da vida... :\\",
                    0F)
            )
            ret.add(
                AvaliacaoProduto(9,
                    avaliadores[2],
                    lanches[2],
                    "é uma monstruosidade mesmo... mata a fome, mas é de susto...",
                    2F)
            )

            return ret
        }

        fun getFromRestaurante(restaurante: Restaurante): ArrayList<AvaliacaoProduto> {
            val avaliacoes = ArrayList<AvaliacaoProduto>()
            for (avaliacao in getSampleArray())
                if (avaliacao.lanche.restaurante.id.equals(restaurante.id))
                    avaliacoes.add(avaliacao)
            return avaliacoes
        }

    }
}