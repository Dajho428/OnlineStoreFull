package edu.eam.ingesoft.onlineStore2.controllers

import edu.eam.ingesoft.onlineStore2.model.entitys.User
import edu.eam.ingesoft.onlineStore2.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @PostMapping("/create/{idCity}")
    fun createUser(@PathVariable("idCity") idCity: Int, @RequestBody user: User) {
        userService.createUser(idCity,user)
    }

    @PutMapping("/update/{idUser}")
    fun updateUser(@PathVariable("idUser") idUser: String, @RequestBody user: User) {
        user.id = idUser
        userService.updateUser(idUser, user)
    }
}