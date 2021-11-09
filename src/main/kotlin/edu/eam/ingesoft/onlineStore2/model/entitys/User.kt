package edu.eam.ingesoft.onlineStore2.model.entitys

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_usuarios")
data class User(
    @Id
    @Column(name = "id_usuario")
    var id: String,

    @Column(name = "direccion")
    var address: String,

    @Column(name = "apellido")
    var lastName: String,

    @Column(name = "nombre")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    var city: City

) : Serializable
