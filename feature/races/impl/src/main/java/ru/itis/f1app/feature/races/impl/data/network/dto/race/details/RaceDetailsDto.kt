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
data class RaceDetailsDto(
    @SerialName("round") val round: String,
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("raceName") val raceName: String,
    @SerialName("url") val url: String,
    @SerialName("circuit") val circuit: CircuitDto,
    @SerialName("results") val results: List<ResultDto>
)
