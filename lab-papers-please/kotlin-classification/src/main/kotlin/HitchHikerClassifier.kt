package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class HitchHikerClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val hitchHikerIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val correctnessChecks = listOf<(JsonNode) -> Boolean>(
                { it["isHumanoid"] != null },
                { it["planet"] != null },
                { it["age"] != null },
                { it["traits"] != null }
            )

            val criteria = listOf(
                // Betelgeusian traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Betelgeuse", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..100 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("EXTRA_ARMS") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("EXTRA_HEAD") == true } to true,

                // Vogon traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == false } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Vogsphere", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..200 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("GREEN") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BULKY") == true } to true
            )

            if (CommonClassifier.classifyIndividual(individual, criteria, correctnessChecks)) {
                hitchHikerIndividuals.add(individual)
            }
        }

        return hitchHikerIndividuals
    }
}