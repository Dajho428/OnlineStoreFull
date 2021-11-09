package edu.eam.ingesoft.onlineStore2.controllersTest

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlineStore2.model.entitys.Category
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
@AutoConfigureMockMvc//Servidor web
class CategoryControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun createCategoryHappyPastTest() {
        val body = """
            {"id":2,
            "name":"Jeans"
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/categories/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createCategoryAlreadyExistTest() {
        val category=Category(1,"Jeans")
        entityManager.persist(category)
        val body = """
            {"id":1,
            "name":"Jeans"
            }""".trimIndent()
        val request =
            MockMvcRequestBuilders.post("/categories/create").contentType(MediaType.APPLICATION_JSON).content(body)
        val response = mockMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This Category already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

}
