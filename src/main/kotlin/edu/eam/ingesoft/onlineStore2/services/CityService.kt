package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.repositorios.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {
    @Autowired
    lateinit var cityRepository: CityRepository

    fun createCity(city: City) {
        val cityById = cityRepository.find(city.id)
        if (cityById != null) {
            throw BusinessException("This City already Exists")
        }
        cityRepository.create(city)
    }
}