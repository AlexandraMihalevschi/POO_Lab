package oop.practice

import com.fasterxml.jackson.databind.JsonNode

class RingsClassifier {
    fun classify(individuals: List<JsonNode>): MutableList<JsonNode> {
        val ringsIndividuals = mutableListOf<JsonNode>()

        individuals.forEach { individual ->
            val primaryChecks = listOf(
                { indiv: JsonNode -> indiv["isHumanoid"] != null } to true,
                { indiv: JsonNode -> indiv["planet"] != null } to true,
                { indiv: JsonNode -> indiv["age"] != null } to true,
                { indiv: JsonNode -> indiv["traits"] != null } to true
            )

            val criteria = listOf(
                // Elf traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Earth", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> indiv["age"]?.asInt() ?: -1 >= 0 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BLONDE") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("POINTY_EARS") == true } to true,

                // Dwarf traits
                { indiv: JsonNode -> indiv["isHumanoid"]?.asBoolean() == true } to true,
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Earth", ignoreCase = true) == true } to true,
                { indiv: JsonNode -> indiv["age"]?.asInt() ?: -1 in 0..200 } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("SHORT") == true } to true,
                { indiv: JsonNode -> indiv["traits"]?.map { it.asText() }?.contains("BULKY") == true } to true
            )

            val correctnessChecks = listOf(
                { indiv: JsonNode -> indiv["planet"]?.asText()?.equals("Earth", ignoreCase = true) == true }
            )

            if (CommonClassifier.classifyIndividual(individual, primaryChecks, criteria, correctnessChecks)) {
                ringsIndividuals.add(individual)
            }
        }

        return ringsIndividuals
    }
}