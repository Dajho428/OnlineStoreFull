package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.model.entitys.User
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
class UserControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createUserHappyPastTest() {
        entityManager.persist(City(1, "Cali"))
        val body = """{
            "id":"111",
            "address":"cr 8",
            "lastName":"Hernandez",
            "name":"Jhonatan",
            "city":{
                "id":1,
                "name":"Cali"
                }
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/users/create/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createUserAlreadyExistTest() {
        val city = City(1, "Cali")
        entityManager.persist(city)
        entityManager.persist(User("111", "Muy Lejos", "Hernandez", "Jhonatan", city))
        val body = """{
            "id":"111",
            "address":"cr 8",
            "lastName":"Hernandez",
            "name":"Jhonatan",
            "city":{
                "id":1,
                "name":"Cali"
                }
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/users/create/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"This user already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString
        )
    }

    @Test
    fun createUserCityNotExistTest() {
        entityManager.persist(City(1, "Cali"))
        val body = """{
            "id":"111",
            "address":"cr 8",
            "lastName":"Hernandez",
            "name":"Jhonatan",
            "city":{
                "id":2,
                "name":"Cali"
                }
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/users/create/2").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals(
            "{\"message\":\"This City doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun updateUserHappyPastTest(){
        val city = City(1, "Cali")
        entityManager.persist(city)
        entityManager.persist(User("111", "Muy Lejos", "Hernandez", "Jhonatan", city))
        val body="""{
            "id":"111",
            "address":"cr 50",
            "lastName":"Herrera",
            "name":"David",
            "city":{
                "id":1,
                "name":"Cali"
                }
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/users/update/111").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }
    @Test
    fun updateUserNotFoundTest(){
        val city = City(1, "Cali")
        entityManager.persist(city)
        entityManager.persist(User("111", "Muy Lejos", "Hernandez", "Jhonatan", city))
        val body="""{
            "id":"111",
            "address":"cr 50",
            "lastName":"Herrera",
            "name":"David",
            "city":{
                "id":1,
                "name":"Cali"
                }
            }""".trimIndent()
        val request = MockMvcRequestBuilders.put("/users/update/1").contentType(MediaType.APPLICATION_JSON).content(body)
        val response=mockMvc.perform(request)
        val resp=response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This user doesn't exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }


}