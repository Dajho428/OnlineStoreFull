package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.model.entitys.Store
import edu.eam.ingesoft.onlineStore2.repositorios.CityRepository
import edu.eam.ingesoft.onlineStore2.repositorios.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class StoreService {
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createStore(store: Store, idCity: Int) {
        val storeAux = storeRepository.find(store.id)
        if (storeAux != null) {
            throw BusinessException("This store already exists")
        }
        var city: City? = cityRepository.find(idCity) ?: throw BusinessException("This City doesn't exists")
        store.city = city
        storeRepository.create(store)
    }

    fun updateStore(idStore: Int, store: Store) {
        val storeAux = storeRepository.find(idStore) ?: throw BusinessException("This store doesn't exist")
        store.id = idStore
        storeRepository.update(store)
    }

    fun listAllStores(): List<Store> {
        val listStores=storeRepository.listAllStores()
        if(listStores.isEmpty()){
            throw BusinessException("There is not exists Stores")
        }
        return listStores
    }
}