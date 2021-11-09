package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.City
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
class CityControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun createCityHappyPastTest() {
        val body = """
            {"id":1,
            "name":"Cali"
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/cities/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createCityAlreadyExistYTest() {
        val city=City(1,"Medellin")
        entityManager.persist(city)
        val body = """
            {"id":1,
            "name":"Medellin"
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/cities/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This City already Exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
}