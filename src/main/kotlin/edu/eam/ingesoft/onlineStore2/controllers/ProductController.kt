package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.Product
import edu.eam.ingesoft.onlineStore2.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @PostMapping("/create/{idCategory}")
    fun createProduct(@PathVariable("idCategory") id_category: Int, @RequestBody product: Product) {
        productService.createProduct(product, id_category)
    }

    @PutMapping("/update/{idProduct}")
    fun updateProduct(@PathVariable("idProduct") id_product: String, @RequestBody product: Product) {
        product.id = id_product
        productService.updateProduct(id_product, product)
    }
}