package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.model.entitys.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StoreControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createStoreHappyPastTest(){
        entityManager.persist(City(1,"Cali"))
        val body="""{
            "id":1,
            "address":"cr 8",
            "name":"All Clothes"            
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/stores/create/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }
    @Test
    fun createStoreAlreadyExistTest(){
        val city=City(1,"Cali")
        entityManager.persist(city)
        entityManager.persist(Store(1,"cra 8","All Clothes",city))
        val body="""{
            "id":1,
            "address":"cr 15",
            "name":"XD"            
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/stores/create/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun createStoreCityNotExist(){
        entityManager.persist(City(1,"Cali"))
        val body="""{
            "id":1,
            "address":"cr 8",
            "name":"All Clothes"            
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/stores/create/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This City doesn't exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun updateStoreHappyPastTest(){
        val city=City(1,"Cali")
        entityManager.persist(city)
        entityManager.persist(Store(1,"cra 8","All Clothes",city))
        val body="""{
            "id":1,
            "address":"cr 50",
            "name":"OnlyClothes"            
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/stores/update/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(200, resp.status)

    }
    @Test
    fun updateStoreNotFoundTest(){
        val city=City(1,"Cali")
        entityManager.persist(city)
        entityManager.persist(Store(1,"cra 8","All Clothes",city))
        val body="""{
            "id":2,
            "address":"cr 50",
            "name":"OnlyClothes"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/stores/update/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun getAllStoresHappyPastTest(){
        val city=City(1,"Cali")
        entityManager.persist(city)
        entityManager.persist(Store(1,"cra 8","All Clothes",city))
        entityManager.persist(Store(2,"cll 80","Only Clothes",city))
        entityManager.persist(Store(3,"cra 50","Super Clothes",city))
        val request=MockMvcRequestBuilders.get("/stores/list").contentType(MediaType.APPLICATION_JSON)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        val store = objectMapper.readValue(resp.contentAsString, Array<Store>::class.java)
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals(3, store.size)
        Assertions.assertEquals("All Clothes",store[0].name)
        Assertions.assertEquals(2,store[1].id)
        Assertions.assertEquals(3,store[2].id)
    }

    @Test
    fun getAllStoreNotFoundTest(){
        val request=MockMvcRequestBuilders.get("/stores/list").contentType(MediaType.APPLICATION_JSON)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"There is not exists Stores\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
}