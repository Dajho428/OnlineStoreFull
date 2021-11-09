package edu.eam.ingesoft.onlineStore2.model.entitys

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_productos_tienda")
data class ProductStore(
    @Id
    @Column(name = "id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "id_producto")
    var product: Product?,

    @ManyToOne
    @JoinColumn(name = "id_tienda")
    var store: Store,

    @Column(name = "precio")
    var price: Double,

    @Column(name = "cantidad")
    var stock: Int
) : Serializable
