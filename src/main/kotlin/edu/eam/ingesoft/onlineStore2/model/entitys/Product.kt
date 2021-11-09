package edu.eam.ingesoft.onlineStore2.model.entitys

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_products")
data class Product(
    @Id
    @Column(name = "id_producto")
    var id: String,

    @Column(name = "marca")
    var branch: String,

    @Column(name = "nombre")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    var category: Category?
) : Serializable
