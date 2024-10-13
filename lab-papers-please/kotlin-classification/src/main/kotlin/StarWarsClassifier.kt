package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class StarWarsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val starWarsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val correctnessChecks = listOf<(JsonNode) -> Boolean>(
                { it["isHumanoid"] != null },
                { it["planet"] != null },
                { it["age"] != null },
                { it["traits"] != null }
            )

            val criteria = listOf(
                // Wookiee traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == false } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Kashyyyk", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..400 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("HAIRY") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("TALL") == true } to true,

                // Ewok traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == false } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Endor", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..60 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("SHORT") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("HAIRY") == true } to true
            )

            if (CommonClassifier.classifyIndividual(individual, criteria, correctnessChecks)) {
                starWarsIndividuals.add(individual)
            }
        }

        return starWarsIndividuals
    }
}