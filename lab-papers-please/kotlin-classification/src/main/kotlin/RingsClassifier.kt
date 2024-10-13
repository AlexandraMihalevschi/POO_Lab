package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class RingsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val ringsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val correctnessChecks = listOf<(JsonNode) -> Boolean>(
                { it["isHumanoid"] != null },
                { it["planet"] != null },
                { it["age"] != null },
                { it["traits"] != null }
            )

            val criteria = listOf(
                // Elf traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Earth", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) >= 0 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BLONDE") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("POINTY_EARS") == true } to true,

                // Dwarf traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Earth", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> (indiv["age"]?.asInt() ?: -1) in 0..200 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("SHORT") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BULKY") == true } to true
            )

            if (CommonClassifier.classifyIndividual(individual, criteria, correctnessChecks)) {
                ringsIndividuals.add(individual)
            }
        }

        return ringsIndividuals
    }
}