package br.com.fiap.isgood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.model.dao.RestauranteDAO

class RestauranteActivityViewModel : ViewModel() {

    var idRestaurante = MutableLiveData<String>()

    var restaurante = MutableLiveData<Restaurante>()

    init {

        idRestaurante.observeForever { id ->
            restaurante.value = RestauranteDAO.getById(id)
        }

    }


}