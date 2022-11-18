package br.com.fiap.isgood.model.dao

import br.com.fiap.isgood.model.Lanche

object LancheDAO : GenericDAO<Lanche, String>() {
    override fun getById(objId: String): Lanche {
        defaultList.forEach {
            if (it.id.equals(objId))
                return it
        }
        throw Exception("Não foi encontrado lanche com o id = $objId")
    }

    override fun getByFilter(filter: String?): ArrayList<Lanche> {
        return defaultList
    }

    override fun update(obj: Lanche): Lanche {
        defaultList.remove(getById(obj.id))
        return add(obj)
    }

    override fun getSampleData(): ArrayList<Lanche> {

        if (defaultList.size ==0){
            defaultList.add(Lanche(
                "1",
                "Hamburguer Big 250g",
                "Pão brioche com hamburguer 250g, salada, catchup e maionese.",
                "https://img.itdg.com.br/tdg/images/recipes/000/161/709/342919/342919_original.jpg?mode=crop&width=710&height=400",
                3,
                RestauranteDAO.getById("1")
            ))

            defaultList.add(Lanche(
                "2",
                "Hamburguer + Fritas",
                "Hamburguer do dia com batata-frita",
                "https://i.pinimg.com/736x/d8/68/dc/d868dcc36646a649dc1898668711ef6b.jpg",
                4,
                RestauranteDAO.getById("2")
            ))

            defaultList.add(Lanche(
                "3",
                "Hambuguer Monstro",
                "Hamburguer big de 350g, no pão brioche, salada e molho especial",
                "https://guiachef.com.br/wp-content/uploads/2019/07/10-ex%C3%B3ticos-hamb%C3%BArgueres-do-mundo-Vegan.jpg",
                5,
                RestauranteDAO.getById("3")
            ))
        }
        return defaultList
    }

}