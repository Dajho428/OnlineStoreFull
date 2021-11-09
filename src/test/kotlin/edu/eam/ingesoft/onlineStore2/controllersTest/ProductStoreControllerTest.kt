package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.*
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
class ProductStoreControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createProductStoreHappyPastTest() {
        val category = Category(1, "Tenis")
        entityManager.persist(category)
        val city = City(1, "Cali")
        entityManager.persist(city)
        val product = Product("01", "Diesel", "Diesel Max", category)
        entityManager.persist(product)
        val store = Store(1, "Muy Lejos", "Donde Cami", city)
        entityManager.persist(store)
        val body = """{            
                "id_productStore": 1,
                "id_store": 1,
                "id_product": "01",
                "stock": 100,
                "price": 123000            
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/productStore/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createProductStoreNotFoundStore() {
        val category = Category(1, "Tenis")
        entityManager.persist(category)
        val city = City(1, "Cali")
        entityManager.persist(city)
        val product = Product("01", "Diesel", "Diesel Max", category)
        entityManager.persist(product)
        val store = Store(1, "Muy Lejos", "Donde Cami", city)
        entityManager.persist(store)
        val body = """{            
                "id_productStore": 1,
                "id_store": 2,
                "id_product": "01",
                "stock": 100,
                "price": 123000            
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/productStore/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"The Store doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString
        )
    }

    @Test
    fun createProductStoreNotFoundProduct() {
        val category = Category(1, "Tenis")
        entityManager.persist(category)
        val city = City(1, "Cali")
        entityManager.persist(city)
        val product = Product("01", "Diesel", "Diesel Max", category)
        entityManager.persist(product)
        val store = Store(1, "Muy Lejos", "Donde Cami", city)
        entityManager.persist(store)
        val body = """{            
                "id_productStore": 1,
                "id_store": 1,
                "id_product": "02",
                "stock": 100,
                "price": 123000            
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/productStore/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"The product doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString
        )
    }

    @Test
    fun getListSProductsByStoreHappyPastTest() {
        createProductStore()
        val request =
            MockMvcRequestBuilders.get("/productStore/list/2").contentType(MediaType.APPLICATION_JSON)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        val product = objectMapper.readValue(resp.contentAsString, Array<Product>::class.java)
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals(3, product.size)
    }

    @Test
    fun getListSProductsByStoreNotFoundTest() {
        createProductStore()
        val request =
            MockMvcRequestBuilders.get("/productStore/list/1").contentType(MediaType.APPLICATION_JSON)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"There is not exists Products\",\"code\":412}".trimIndent(),
            resp.contentAsString
        )
    }

    @Test
    fun getListProductsStoreByCategoryHappyPastTest() {
        createProductStore()
        val request =
            MockMvcRequestBuilders.get("/productStore/list/2/2").contentType(MediaType.APPLICATION_JSON)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        val product = objectMapper.readValue(resp.contentAsString, Array<Product>::class.java)
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals(1, product.size)
    }

    @Test
    fun getListProductsStoreByCategoryNotFoundTest() {
        createProductStore()
        val request =
            MockMvcRequestBuilders.get("/productStore/list/2/3").contentType(MediaType.APPLICATION_JSON)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"There is not exists Products for this Category\",\"code\":412}".trimIndent(),
            resp.contentAsString
        )
    }

    private fun createProductStore() {
        val category = Category(1, "Tenis")
        entityManager.persist(category)
        val category2 = Category(2, "Tenis Baloncesto")
        entityManager.persist(category2)
        val city = City(1, "Cali")
        entityManager.persist(city)
        val product = Product("01", "Diesel", "Diesel Max", category)
        entityManager.persist(product)
        val product2 = Product("02", "Nike", "Nike Max", category)
        entityManager.persist(product2)
        val product3 = Product("03", "Adidas", "Airball", category2)
        entityManager.persist(product3)
        val store = Store(1, "Muy Lejos", "Donde Cami", city)
        entityManager.persist(store)
        val store2 = Store(2, "Far Away", "Todo tenis", city)
        entityManager.persist(store2)
        val store3 = Store(3, "Cerca al Mas alla", "Todo Vans", city)
        entityManager.persist(store3)
        val productStore = ProductStore(101, product, store2, 150000.0, 10)
        entityManager.persist(productStore)
        val productStore2 = ProductStore(102, product2, store2, 250000.0, 15)
        entityManager.persist(productStore2)
        val productStore3 = ProductStore(103, product3, store2, 200000.0, 20)
        entityManager.persist(productStore3)
    }
}