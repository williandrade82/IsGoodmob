package br.com.fiap.isgood.utils

import br.com.fiap.isgood.model.Lanche
import br.com.fiap.isgood.model.dao.RestauranteDAO
import br.com.fiap.isgood.model.dto.LancheDTO

class LancheUtils {
    companion object {
        fun convertLanchesDtoTOLanches(lanchesDTOList: List<LancheDTO>): ArrayList<Lanche> {
            var lanchesListResult: ArrayList<Lanche> = ArrayList<Lanche>();
            if (lanchesDTOList.isNotEmpty()) {
                for (lancheDTO in lanchesDTOList) {

                    val lanche: Lanche = Lanche(
                        lancheDTO.id.toString(),
                        lancheDTO.nome,
                        lancheDTO.descricao,
                        lancheDTO.foto,
                        lancheDTO.rating.toInt(),
                        RestauranteDAO.getById(lancheDTO.empresa.id.toString())
                    );

                    lanchesListResult.add(lanche);
                }
            }
            return lanchesListResult;
        }
    }
}