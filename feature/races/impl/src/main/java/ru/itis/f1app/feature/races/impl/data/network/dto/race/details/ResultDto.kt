package ru.itis.f1app.feature.races.impl.data.network.dto.race.details

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

@Serializable
data class ResultDto(
    @Serializable(with = IntOrStringSerializer::class)
    @SerialName("position") val position: String,
    @SerialName("points") val points: Double,
    @Serializable(with = IntOrStringSerializer::class)
    @SerialName("grid") val grid: String,
    @SerialName("time") val time: String? = null,
    @SerialName("driver") val driver: DriverDto,
    @SerialName("team") val team: ConstructorDto
)

object IntOrStringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntOrString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val jsonInput = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
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
