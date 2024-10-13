package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class MarvelClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val marvelIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val primaryChecks = listOf(
                { indiv: JsonNode -> indiv["isHumanoid"] !== null } to true,
                { indiv: JsonNode -> indiv["planet"] !== null } to true,
                { indiv: JsonNode -> indiv["age"] !== null } to true,
                { indiv: JsonNode -> indiv["traits"] !== null } to true
            )

            val criteria = listOf(
                // Asgardian traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Asgard", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..5000 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BLONDE") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("TALL") == true } to true
            )

            val correctnessChecks = listOf(
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Asgard", ignoreCase = true) == true }
            )

            if (CommonClassifier.classifyIndividual(individual, primaryChecks, criteria, correctnessChecks)) {
                marvelIndividuals.add(individual)
            }
        }

        return marvelIndividuals
    }
}