package edu.eam.ingesoft.onlineStore2.model.entitys

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_categorias")
data class Category(
    @Id
    @Column(name = "id_categoria")
    val id: Int,

    @Column(name = "nombre")
    var name: String
) : Serializable
