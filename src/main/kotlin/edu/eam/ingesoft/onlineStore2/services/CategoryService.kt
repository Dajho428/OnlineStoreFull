package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.Category
import edu.eam.ingesoft.onlineStore2.repositorios.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createCategory(category: Category) {
        val categoryById = categoryRepository.find(category.id)
        if (categoryById != null) {
            throw BusinessException("This Category already exists")
        }
        categoryRepository.create(category)
    }
}