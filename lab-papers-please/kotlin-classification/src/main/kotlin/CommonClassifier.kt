package oop.practice

import com.fasterxml.jackson.databind.JsonNode

object CommonClassifier {
    const val TRUST_THRESHOLD = 1

    // Perform the initial filtering based on primary characteristics and trust rank calculation
    fun classifyIndividual(
        individual: JsonNode,
        primaryChecks: List<Pair<(JsonNode) -> Boolean, Boolean>>, // Checks for initial filtering
        criteria: List<Pair<(JsonNode) -> Boolean, Boolean>>, // Criteria for calculating trust rank
        correctnessChecks: List<(JsonNode) -> Boolean> // Checks for verification after trust rank calculation
    ): Boolean {
        // Perform primary checks
        val primaryCheckPassed = primaryChecks.all { (check, expected) -> check(individual) == expected }

        if (!primaryCheckPassed) {
            return false
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

        // Perform correctness checks
        return trustRank > TRUST_THRESHOLD && correctnessChecks.all { it(individual) }
    }
}