package oop.practice

import com.fasterxml.jackson.databind.JsonNode

object CommonClassifier {
    const val TRUST_THRESHOLD = 0

    // Initial filtering based on primary characteristics and trust rank calculation
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
            if (result == expected) { //Add if the result is explicitly correct
                trustRank++
            } else if (result == false) { // Only subtract if the result is explicitly false
                trustRank--
            }
        }

        // Perform correctness checks again
        return trustRank >= TRUST_THRESHOLD && correctnessChecks.all { it(individual) }
    }
}