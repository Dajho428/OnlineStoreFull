package edu.eam.ingesoft.onlineStore2.repositorios

import edu.eam.ingesoft.onlineStore2.model.entitys.Store
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Component
@Transactional
class StoreRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create(store: Store) {
        em.persist(store)
    }

    fun find(id: Int): Store? {
        return em.find(Store::class.java, id)
    }

    fun update(store: Store) {
        em.merge(store)
    }

    fun delete(id: Int) {
        val store = find(id)

        if (store != null) {
            em.remove(store)
        }
    }

    fun listAllStores(): List<Store> {
        val query = em.createQuery("SELECT store FROM Store store")
        return query.resultList as List<Store>
    }


}
