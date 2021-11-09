package edu.eam.ingesoft.onlineStore2.model.request

data class ProductStoreRequestCreate(
    val id_productStore: Int,
    val id_store: Int,
    val id_product: String,
    val stock: Int,
    val price: Double,
)
