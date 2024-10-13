package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class RingsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val ringsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val isHumanoid = individual["isHumanoid"]?.asBoolean() ?: false
            val age = individual["age"]?.asInt() ?: -1
            val planet = individual["planet"]?.asText() ?: ""
            val traits = individual["traits"]?.map { it.asText() } ?: emptyList()

            if ((isHumanoid && planet.equals("Earth", ignoreCase = true) && traits.containsAll(listOf("BLONDE", "POINTY_EARS"))) ||
                (isHumanoid && planet.equals("Earth", ignoreCase = true) && age in 0..200 && traits.containsAll(listOf("SHORT", "BULKY")))) {
                ringsIndividuals.add(individual)
            }
        }

        return ringsIndividuals
    }
}