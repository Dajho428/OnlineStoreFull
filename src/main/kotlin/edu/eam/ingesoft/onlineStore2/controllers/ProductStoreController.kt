package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.Product
import edu.eam.ingesoft.onlineStore2.model.request.ProductStoreRequestCreate
import edu.eam.ingesoft.onlineStore2.services.ProductStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productStore")
class ProductStoreController {
    @Autowired
    lateinit var productStoreService: ProductStoreService

    @PostMapping("/create")
    fun createProductStore(@RequestBody productStoreRequestCreate: ProductStoreRequestCreate) {
        productStoreService.createProductStore(productStoreRequestCreate)
    }

    @GetMapping("/list/{idStore}")
    fun getListSProductsByStore(@PathVariable("idStore") idStore: Int): List<Product> {
        return productStoreService.listStoreProducts(idStore)
    }

    @GetMapping("/list/{idStore}/{idCategory}")
    fun getListProductsStoreByCategory(
        @PathVariable("idStore") idStore: Int,
        @PathVariable("idCategory") idCategory: Int
    ): List<Product> {
        return productStoreService.listStoreProductsByCategory(idStore, idCategory)
    }
}