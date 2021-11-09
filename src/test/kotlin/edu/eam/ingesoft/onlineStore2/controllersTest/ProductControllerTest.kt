package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.Category
import edu.eam.ingesoft.onlineStore2.model.entitys.Product
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
class ProductControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun createProductHappyPastTest() {
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        val body = """{
            "id":"001",
            "branch":"Diesel",
            "name":"Diesel Max"            
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/products/create/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }
    @Test
    fun createProductAlreadyExistTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        val product=Product("001","Diesel","Diesel max",category)
        entityManager.persist(product)
        val body = """{
            "id":"001",
            "branch":"Diesel",
            "name":"Diesel Max2"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/products/create/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun createProductNameAlreadyExistTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        val product=Product("001","Diesel","Diesel Max2",category)
        entityManager.persist(product)
        val body = """{
            "id":"002",
            "branch":"Diesel",
            "name":"Diesel Max2"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/products/create/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun createProductCategoryNotExistTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        val body = """{
            "id":"002",
            "branch":"Diesel",
            "name":"Diesel Max2"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.post("/products/create/3").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This Category doesn't existing\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun updateProductHappyPastTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        entityManager.persist(Product("1","Nike","Nike KB8",category))
        val body = """{
            "id":"1",
            "branch":"Diesel",
            "name":"Diesel Max"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/products/update/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }
    @Test
    fun updateProductNotFoundTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        entityManager.persist(Product("1","Nike","Nike KB8",category))
        val body = """{
            "id":"1",
            "branch":"Diesel",
            "name":"Diesel Max"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/products/update/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun updateProductNameExistTest(){
        val category = Category(2, "Tenis")
        entityManager.persist(category)
        entityManager.persist(Product("1","Nike","Diesel Max",category))
        val body = """{
            "id":"1",
            "branch":"Diesel",
            "name":"Diesel Max"
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/products/update/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
}