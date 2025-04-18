package com.example.ecommerce_app.core.common

import com.example.ecommerce_app.data.source.dto.CartEntity
import com.example.ecommerce_app.domain.model.Cart

object CartMapper {

    fun entityToDomain(entity: CartEntity): Cart {
        return Cart(
            id = entity.id,
            title = entity.title,
            price = entity.price,
            category = entity.category,
            description = entity.description,
            image = entity.image,
            quantity = entity.quantity,
        )
    }

    fun domainToEntity(product: Cart): CartEntity {
        return CartEntity(
            id = product.id,
            title = product.title,
            price = product.price,
            category = product.category,
            description = product.description,
            image = product.image,
            quantity = product.quantity
        )
    }

    fun entityListToDomainList(entities: List<CartEntity>): List<Cart> {
        return entities.map { entityToDomain(it) }
    }

    fun domainListToEntityList(products: List<Cart>): List<CartEntity> {
        return products.map { domainToEntity(it) }
    }
}
