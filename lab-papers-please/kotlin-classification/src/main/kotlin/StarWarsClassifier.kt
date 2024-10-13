package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class StarWarsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val starWarsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val isHumanoid = individual["isHumanoid"]?.asBoolean() ?: false
            val age = individual["age"]?.asInt() ?: -1
            val planet = individual["planet"]?.asText() ?: ""
            val traits = individual["traits"]?.map { it.asText() } ?: emptyList()

            if ((!isHumanoid && planet.equals("Kashyyyk", ignoreCase = true) && age in 0..400 && traits.containsAll(listOf("HAIRY", "TALL"))) ||
                (!isHumanoid && planet.equals("Endor", ignoreCase = true) && age in 0..60 && traits.containsAll(listOf("SHORT", "HAIRY")))) {
                starWarsIndividuals.add(individual)
            }
        }

        return starWarsIndividuals
    }
}