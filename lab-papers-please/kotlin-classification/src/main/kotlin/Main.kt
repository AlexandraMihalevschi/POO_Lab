package oop.practice

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.InputStream

fun main() {
  val objectMapper = ObjectMapper()
  val inputStream: InputStream? = object {}.javaClass.getResourceAsStream("/test-input.json")

  val jsonParser = JsonParser(objectMapper)
  val individuals = jsonParser.parseJsonFile(inputStream)

  // Print all individuals
  jsonParser.printIndividuals(individuals)

  // Create universes
  val starWars = Universe("starWars", mutableListOf())
  val hitchHiker = Universe("hitchHiker", mutableListOf())
  val rings = Universe("rings", mutableListOf())
  val marvel = Universe("marvel", mutableListOf())

  // Manual sorting part
  individuals.forEach {
    println(it)

    when (readlnOrNull()) {
      "4" -> starWars.individuals.add(it)
      "1" -> hitchHiker.individuals.add(it)
      "3" -> rings.individuals.add(it)
      "2" -> marvel.individuals.add(it)
    }
  }

  // Write to output files
  writeUniverseToFile(objectMapper, "src/main/resources/output/starwars.json", starWars)
  writeUniverseToFile(objectMapper, "src/main/resources/output/hitchhiker.json", hitchHiker)
  writeUniverseToFile(objectMapper, "src/main/resources/output/rings.json", rings)
  writeUniverseToFile(objectMapper, "src/main/resources/output/marvel.json", marvel)
}

fun writeUniverseToFile(objectMapper: ObjectMapper, filePath: String, universe: Universe) {
  val file = File(filePath)
  file.parentFile.mkdirs() // Create directories if they do not exist
  file.writeText(objectMapper.writeValueAsString(universe))
}

data class Universe(
  val name: String,
  val individuals: MutableList<JsonNode>
)