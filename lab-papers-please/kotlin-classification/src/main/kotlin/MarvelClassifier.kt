package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class MarvelClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val marvelIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val isHumanoid = individual["isHumanoid"]?.asBoolean() ?: false
            val age = individual["age"]?.asInt() ?: -1
            val planet = individual["planet"]?.asText() ?: ""
            val traits = individual["traits"]?.map { it.asText() } ?: emptyList()

            if (isHumanoid && planet.equals("Asgard", ignoreCase = true) && age in 0..5000 && traits.containsAll(listOf("BLONDE", "TALL"))) {
                marvelIndividuals.add(individual)
            }
        }

        return marvelIndividuals
    }
}