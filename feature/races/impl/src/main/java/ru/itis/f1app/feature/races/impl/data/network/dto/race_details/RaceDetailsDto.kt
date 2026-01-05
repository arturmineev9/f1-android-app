package ru.itis.f1app.feature.races.impl.data.network.dto.race_details

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull

@Serializable
data class RaceDetailsResponse(
    @SerialName("races") val race: RaceDetailsDto
)

@Serializable
data class RaceDetailsDto(
    val round: String,
    val date: String,
    val time: String,
    val raceName: String,
    val url: String,
    @SerialName("circuit") val circuit: CircuitDto,
    @SerialName("results") val results: List<ResultDto>
)

@Serializable
data class ResultDto(
    @Serializable(with = IntOrStringSerializer::class)
    val position: String,
    val points: Double,
    @Serializable(with = IntOrStringSerializer::class)
    val grid: String,
    val time: String? = null,
    @SerialName("driver") val driver: DriverDto,
    @SerialName("team") val team: ConstructorDto
)

@Serializable
data class DriverDto(
    val driverId: String,
    val code: String? = null,
    val url: String,
    @SerialName("name") val givenName: String,
    @SerialName("surname") val familyName: String,
    val dateOfBirth: String? = null,
    val nationality: String
)

@Serializable
data class ConstructorDto(
    val teamId: String,
    val url: String,
    @SerialName("teamName") val name: String,
    val nationality: String
)

@Serializable
data class CircuitDto(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    @SerialName("city") val locality: String,
    val country: String
)

object IntOrStringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntOrString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val jsonInput = decoder as? JsonDecoder ?: throw IllegalStateException("Can be deserialized only by JSON")
        val json = jsonInput.decodeJsonElement()

        return if (json is JsonPrimitive) {
            if (json.isString) json.content else json.content
        } else {
            json.toString()
        }
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}
