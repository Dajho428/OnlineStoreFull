package edu.eam.ingesoft.onlineStore2.model.entitys

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_tienda")
data class Store(
    @Id
    @Column(name = "id_tienda")
    var id: Int,

    @Column(name = "direccion")
    var address: String,

    @Column(name = "nombre")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    var city: City?
) : Serializable
