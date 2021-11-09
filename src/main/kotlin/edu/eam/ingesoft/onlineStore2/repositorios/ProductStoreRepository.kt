package edu.eam.ingesoft.onlineStore2.repositorios

import edu.eam.ingesoft.onlineStore2.model.entitys.Product
import edu.eam.ingesoft.onlineStore2.model.entitys.ProductStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ProductStoreRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create(productStore: ProductStore) {
        em.persist(productStore)
    }

    fun find(id: Int): ProductStore? {
        return em.find(ProductStore::class.java, id)
    }

    fun update(productStore: ProductStore) {
        em.merge(productStore)
    }

    fun delete(id: Int) {
        val productStore = find(id)

        if (productStore != null) {
            em.remove(productStore)
        }
    }

    fun listStoreProducts(idStore: Int): List<Product> {
        val query =
            em.createQuery("SELECT productStore.product FROM ProductStore productStore WHERE productStore.store.id = : idStore")
        query.setParameter("idStore", idStore)
        return query.resultList as List<Product>
    }

    fun listStoreProductsByCategory(idStore: Int, idCategory: Int): List<Product> {
        val query =
            em.createQuery(
                "SELECT productStore.product FROM ProductStore productStore WHERE productStore.product.category.id = :idCategory " +
                        "and productStore.store.id = :idStore"
            )
        query.setParameter("idCategory", idCategory)
        query.setParameter("idStore", idStore)
        return query.resultList as List<Product>
    }

}
