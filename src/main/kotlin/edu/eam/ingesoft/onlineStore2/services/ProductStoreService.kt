package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.Product
import edu.eam.ingesoft.onlineStore2.model.entitys.ProductStore
import edu.eam.ingesoft.onlineStore2.model.entitys.Store
import edu.eam.ingesoft.onlineStore2.model.request.ProductStoreRequestCreate
import edu.eam.ingesoft.onlineStore2.repositorios.ProductRepository
import edu.eam.ingesoft.onlineStore2.repositorios.ProductStoreRepository
import edu.eam.ingesoft.onlineStore2.repositorios.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ProductStoreService {

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    fun createProductStore(productStoreRequestCreate: ProductStoreRequestCreate) {
        var store: Store = storeRepository.find(productStoreRequestCreate.id_store)
            ?: throw BusinessException("The Store doesn't exist")
        var product: Product? = productRepository.find(productStoreRequestCreate.id_product)
            ?: throw BusinessException("The product doesn't exist")
        val productStore = ProductStore(
            productStoreRequestCreate.id_productStore,
            product,
            store,
            productStoreRequestCreate.price,
            productStoreRequestCreate.stock
        )
        productStoreRepository.create(productStore)
    }

    fun decreaseStock(idProductStore: Int, decreaseStock: Int) {
        val productStore = productStoreRepository.find(idProductStore)

        if (productStore == null) {
            throw BusinessException("This product store doesn't exist")
        }
        if (decreaseStock > productStore.stock) {
            throw BusinessException("There can't be less than zero stocks")
        }
        productStore.stock = productStore.stock - decreaseStock
        productStoreRepository.update(productStore)


    }

    fun increaseStock(idProductStore: Int, increaseStock: Int) {

        val productStore = productStoreRepository.find(idProductStore)

        if (productStore == null) {
            throw BusinessException("This product store doesn't exist")
        }
        productStore.stock = productStore.stock + increaseStock
        productStoreRepository.update(productStore)
    }

    fun listStoreProducts(idStore: Int): List<Product> {
        val listProducts=productStoreRepository.listStoreProducts(idStore)
        if(listProducts.isEmpty()){
            throw BusinessException("There is not exists Products")
        }
        return listProducts
    }

    fun listStoreProductsByCategory(idStore: Int, idCategory: Int): List<Product> {
        val listProducts=productStoreRepository.listStoreProductsByCategory(idStore, idCategory)
        if(listProducts.isEmpty()){
            throw BusinessException("There is not exists Products for this Category")
        }
        return listProducts
    }
}