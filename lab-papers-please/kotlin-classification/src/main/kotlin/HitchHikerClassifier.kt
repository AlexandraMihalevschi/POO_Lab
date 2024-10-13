package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class HitchHikerClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val hitchHikerIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val isHumanoid = individual["isHumanoid"]?.asBoolean() ?: false
            val age = individual["age"]?.asInt() ?: -1
            val planet = individual["planet"]?.asText() ?: ""
            val traits = individual["traits"]?.map { it.asText() } ?: emptyList()

            if ((isHumanoid && planet.equals("Betelgeuse", ignoreCase = true) && age in 0..100 && traits.containsAll(listOf("EXTRA_ARMS", "EXTRA_HEAD"))) ||
                (!isHumanoid && planet.equals("Vogsphere", ignoreCase = true) && age in 0..200 && traits.containsAll(listOf("GREEN", "BULKY")))) {
                hitchHikerIndividuals.add(individual)
            }
        }

        return hitchHikerIndividuals
    }
}