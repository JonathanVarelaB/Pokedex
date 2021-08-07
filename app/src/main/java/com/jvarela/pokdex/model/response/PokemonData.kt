package com.jvarela.pokdex.model.response

data class PokemonData (
    val height: String,
    val id: String,
    val name: String,
    val weight: String,
    val abilities: List<AbilityData>,
    val sprites: Sprite,
    val stats: List<StatData>
)

data class Sprite(
    val front_default: String
)

data class StatData(
    val base_stat: String,
    val stat: Stat
)

data class Stat(
    val name: String
)

data class AbilityData (
    val ability: Ability
)

data class Ability(
    val name: String
)

