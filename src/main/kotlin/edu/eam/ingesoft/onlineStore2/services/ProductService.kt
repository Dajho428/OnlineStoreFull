package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.Category
import edu.eam.ingesoft.onlineStore2.model.entitys.Product
import edu.eam.ingesoft.onlineStore2.repositorios.CategoryRepository
import edu.eam.ingesoft.onlineStore2.repositorios.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createProduct(product: Product, id_category: Int) {
        val productAux = productRepository.find(product.id)
        if (productAux != null) {
            throw BusinessException("This product already exists")
        }
        val productByName = productRepository.findByName(product.name)
        if (productByName != null) {
            throw BusinessException("This product with this name already exists")
        }
        var category: Category? =
            categoryRepository.find(id_category) ?: throw BusinessException("This Category doesn't existing")
        product.category = category
        productRepository.create(product)

    }

    fun updateProduct(id_product: String, product: Product) {
        val productById = productRepository.find(id_product) ?: throw BusinessException("This product doesn't exist")
        val productByName = productRepository.findByName(product.name)

        if (productByName != null) {
            throw BusinessException("This product with this name already exists")
        }
        product.category = productById.category
        productRepository.update(product)
    }


}


