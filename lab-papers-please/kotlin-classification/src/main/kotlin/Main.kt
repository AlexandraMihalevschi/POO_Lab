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

  // Classify individuals per universe
  val starWarsClassifier = StarWarsClassifier()
  val hitchHikerClassifier = HitchHikerClassifier()
  val ringsClassifier = RingsClassifier()
  val marvelClassifier = MarvelClassifier()

  val starWarsIndividuals = starWarsClassifier.classify(individuals)
  val hitchHikerIndividuals = hitchHikerClassifier.classify(individuals)
  val ringsIndividuals = ringsClassifier.classify(individuals)
  val marvelIndividuals = marvelClassifier.classify(individuals)

  // Create Universe objects
  val starWarsUniverse = Universe("starWars", starWarsIndividuals)
  val hitchHikerUniverse = Universe("hitchHiker", hitchHikerIndividuals)
  val ringsUniverse = Universe("rings", ringsIndividuals)
  val marvelUniverse = Universe("marvel", marvelIndividuals)

  // Write to output files
  writeUniverseToFile(objectMapper, "src/main/resources/output/starwars.json", starWarsUniverse)
  writeUniverseToFile(objectMapper, "src/main/resources/output/hitchhiker.json", hitchHikerUniverse)
  writeUniverseToFile(objectMapper, "src/main/resources/output/rings.json", ringsUniverse)
  writeUniverseToFile(objectMapper, "src/main/resources/output/marvel.json", marvelUniverse)
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