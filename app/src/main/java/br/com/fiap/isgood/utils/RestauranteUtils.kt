package br.com.fiap.isgood.utils

import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.model.dao.RestauranteDAO
import br.com.fiap.isgood.model.dto.EmpresasDTO

class RestauranteUtils {
    companion object {
        fun convertEmpresasDtoTORestaurantes(empresaDTOlist: List<EmpresasDTO>): ArrayList<Restaurante> {
            var restaurantesListResult: ArrayList<Restaurante> = ArrayList<Restaurante>();
            if (empresaDTOlist.isNotEmpty()) {
                for (empresa in empresaDTOlist) {
                    val socialLinks: ArrayList<Restaurante.SocialLinks> =
                        ArrayList<Restaurante.SocialLinks>();

                    if (empresa.url_facebook != null && empresa.url_facebook != "") {
                        socialLinks.add(
                            Restaurante.SocialLinks(
                                Restaurante.SocialLinks.KIND_FACEBOOK,
                                "FB " + empresa.nome,
                                empresa.url_facebook
                            )
                        )
                    }

                    if (empresa.url_instagram != null && empresa.url_instagram != "") {
                        socialLinks.add(
                            Restaurante.SocialLinks(
                                Restaurante.SocialLinks.KIND_INSTAGRAM,
                                "Instagram " + empresa.nome,
                                empresa.url_instagram
                            )
                        )
                    }

                    if (empresa.url_twitter != null && empresa.url_twitter != "") {
                        socialLinks.add(
                            Restaurante.SocialLinks(
                                Restaurante.SocialLinks.KIND_SITE,
                                "TW " + empresa.nome,
                                empresa.url_twitter
                            )
                        )
                    }

                    if (empresa.url_tripadvisor != null && empresa.url_tripadvisor != "") {
                        socialLinks.add(
                            Restaurante.SocialLinks(
                                Restaurante.SocialLinks.KIND_TRIPADVISOR,
                                "TP " + empresa.nome,
                                empresa.url_tripadvisor
                            )
                        )
                    }

                    val restaurante: Restaurante = Restaurante(
                        empresa.id.toString(),
                        empresa.nome,
                        empresa.apresentacao,
                        empresa.endereco + " " + empresa.cep,
                        empresa.rating.toFloat(),
                        empresa.latitude,
                        empresa.longitude,
                        empresa.logo,
                        socialLinks
                    );

                    restaurantesListResult.add(restaurante);
                }
            }

            return restaurantesListResult;
        }
    }
}