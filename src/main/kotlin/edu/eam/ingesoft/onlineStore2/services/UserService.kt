package edu.eam.ingesoft.onlineStore2.services

import edu.eam.ingesoft.onlineStore2.exceptions.BusinessException
import edu.eam.ingesoft.onlineStore2.model.entitys.City
import edu.eam.ingesoft.onlineStore2.model.entitys.User
import edu.eam.ingesoft.onlineStore2.repositorios.CityRepository
import edu.eam.ingesoft.onlineStore2.repositorios.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var cityRepository: CityRepository

    fun createUser(idCity:Int,user: User) {
        val userAux = userRepository.find(user.id)
        if (userAux != null) {
            throw BusinessException("This user already exists")
        }
        val city: City = cityRepository.find(idCity) ?: throw BusinessException("This City doesn't exist")
        user.city=city
        userRepository.create(user)
    }

    fun updateUser(idUser: String, user: User) {
        val userAux = userRepository.find(idUser) ?: throw BusinessException("This user doesn't exist")
        user.city=userAux.city
        userRepository.update(user)
    }
}