package oop.practice

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Serializable
import java.io.File
import java.io.InputStream

fun main() {
  val objectMapper = ObjectMapper()
  val inputStream: InputStream? = object {}.javaClass.getResourceAsStream("/test-input.json")
  if (inputStream == null) {
    println("File not found: src/main/resources/test-input.json")
    return
  }

  val input = objectMapper.readTree(inputStream)
  val data = input["data"]

  val starWars = Universe("starWars", mutableListOf())
  val hitchHiker = Universe("hitchHiker", mutableListOf())
  val rings = Universe("rings", mutableListOf())
  val marvel = Universe("marvel", mutableListOf())

  data.forEach {
    val node = it
    println(node)

    when (readlnOrNull()) {
      "1" -> starWars.individuals.add(node)
      "2" -> hitchHiker.individuals.add(node)
      "3" -> rings.individuals.add(node)
      "4" -> marvel.individuals.add(node)
    }
  }


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