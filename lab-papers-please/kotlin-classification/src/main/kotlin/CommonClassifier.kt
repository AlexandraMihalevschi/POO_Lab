package oop.practice

import com.fasterxml.jackson.databind.JsonNode

object CommonClassifier {
    fun classifyIndividual(
        individual: JsonNode,
        criteria: List<Pair<(JsonNode) -> Boolean, Boolean>>, // Pair of criterion and expected boolean result
        correctnessChecks: List<(JsonNode) -> Boolean>
    ): Boolean {
        // Check all correctness checks
        if (correctnessChecks.any { !it(individual) }) {
            return false // Indicate invalid individual
        }

        // Calculate trust rank based on criteria
        var trustRank = 0
        criteria.forEach { (criterion, expected) ->
            val result = criterion(individual)
            if (result == expected) {
                trustRank++
            } else if (result != null) {
                trustRank--
            }
        }
        // Return true if trust rank is positive
        return trustRank > 0
    }
}