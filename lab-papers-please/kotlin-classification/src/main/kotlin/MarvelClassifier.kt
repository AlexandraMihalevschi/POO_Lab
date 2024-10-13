package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class MarvelClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val marvelIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val correctnessChecks = listOf<(JsonNode) -> Boolean>(
                { it["isHumanoid"] != null },
                { it["planet"] != null },
                { it["age"] != null },
                { it["traits"] != null }
            )

            val criteria = listOf(
                // Asgardian traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Asgard", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..5000 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BLONDE") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("TALL") == true } to true
            )

            if (CommonClassifier.classifyIndividual(individual, criteria, correctnessChecks)) {
                marvelIndividuals.add(individual)
            }
        }

        return marvelIndividuals
    }
}