package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.Store
import edu.eam.ingesoft.onlineStore2.services.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stores")
class StoreController {
    @Autowired
    lateinit var storeService: StoreService

    @PostMapping("/create/{idCity}")
    fun createStore(@PathVariable("idCity") idCity: Int, @RequestBody store: Store) {
        storeService.createStore(store, idCity)
    }

    @PutMapping("/update/{idStore}")
    fun updateStore(@PathVariable("idStore") idStore: Int, @RequestBody store: Store) {
        store.id = idStore
        storeService.updateStore(idStore, store)
    }

    @GetMapping("/list")
    fun getAllStores(): List<Store> {
        return storeService.listAllStores()
    }
}