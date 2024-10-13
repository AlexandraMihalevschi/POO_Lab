package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class StarWarsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val starWarsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val primaryChecks = listOf(
                { indiv: JsonNode -> indiv["isHumanoid"] != null } to true,
                { indiv: JsonNode -> indiv["planet"] != null } to true,
                { indiv: JsonNode -> indiv["age"] != null } to true,
                { indiv: JsonNode -> indiv["traits"] != null } to true
            )

            val criteria = listOf(
                // Wookiee traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == false } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Kashyyyk", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> indiv["age"]?.asInt() ?: -1 in 0..400 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("HAIRY") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("TALL") == true } to true,

                // Ewok traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == false } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Endor", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> indiv["age"]?.asInt() ?: -1 in 0..60 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("SHORT") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("HAIRY") == true } to true
            )

            val correctnessChecks = listOf(
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Kashyyyk", ignoreCase = true) == true || indiv["planet"]?.asText()?.equals("Endor", ignoreCase = true) == true }
            )

            if (CommonClassifier.classifyIndividual(individual, primaryChecks, criteria, correctnessChecks)) {
                starWarsIndividuals.add(individual)
            }
        }

        return starWarsIndividuals
    }
}