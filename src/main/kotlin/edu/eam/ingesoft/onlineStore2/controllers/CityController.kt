package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cities")
class CityController {
    @Autowired
    lateinit var cityService: CityService

    @PostMapping("/create")
    fun createCity(@RequestBody city: City) {
        cityService.createCity(city)
    }
}