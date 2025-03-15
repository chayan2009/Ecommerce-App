package com.example.ecommerce_app.core.common

import com.example.ecommerce_app.data.source.dto.CartEntity
import com.example.ecommerce_app.data.source.dto.FavouriteEntity
import com.example.ecommerce_app.domain.model.Cart
import com.example.ecommerce_app.domain.model.Favourite

object FavouriteMapper {

    fun entityToDomain(entity: FavouriteEntity): Favourite {
        return Favourite(
            id = entity.id,
            title = entity.title,
            price = entity.price,
            category = entity.category,
            description = entity.description,
            image = entity.image
        )
    }

    fun domainToEntity(favourite: Favourite): FavouriteEntity {
        return FavouriteEntity(
            id = favourite.id,
            title = favourite.title,
            price = favourite.price,
            category = favourite.category,
            description = favourite.description,
            image = favourite.image
        )
    }

    fun entityListToDomainList(entities: List<FavouriteEntity>): List<Favourite> {
        return entities.map { entityToDomain(it) }
    }

    fun domainListToEntityList(products: List<Favourite>): List<FavouriteEntity> {
        return products.map { domainToEntity(it) }
    }
}
