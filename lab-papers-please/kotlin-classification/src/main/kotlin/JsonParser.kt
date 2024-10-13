package oop.practice

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

class JsonParser(private val objectMapper: ObjectMapper) {

    fun parseJsonFile(inputStream: InputStream?): List<JsonNode> {
        if (inputStream == null) {
            println("File not found: src/main/resources/test-input.json")
            return emptyList()
        }

        val rootNode = objectMapper.readTree(inputStream)
        return rootNode["data"].toList()
    }

    fun printIndividuals(individuals: List<JsonNode>) {
        individuals.forEach { println(it) }
    }
}