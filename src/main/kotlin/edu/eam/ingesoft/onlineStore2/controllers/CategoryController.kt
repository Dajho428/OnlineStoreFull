package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.Category
import edu.eam.ingesoft.onlineStore2.services.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController {
    @Autowired
    lateinit var categoryService: CategoryService

    @PostMapping("/create")
    fun createCategory(@RequestBody category: Category) {
        categoryService.createCategory(category)
    }
}