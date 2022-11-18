package br.com.fiap.isgood.model.dao

import br.com.fiap.isgood.model.Usuario

abstract class GenericDAO <DataModel, IdType>{
    var defaultList = ArrayList<DataModel>()

    fun add(obj : DataModel) : DataModel{
        defaultList.add(obj)
        return obj
    }

    fun delete(obj : DataModel) : ArrayList<DataModel>{
        defaultList.remove(obj)
        return defaultList
    }

    abstract  fun getById(objId : IdType): DataModel

    abstract fun update(obj: DataModel) : DataModel

    abstract fun getSampleData(): ArrayList<DataModel>

    abstract fun getByFilter(filter : String? = null) : ArrayList<DataModel>
}