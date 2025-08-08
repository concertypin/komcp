package moe.euroka.komcp.jsonrpc.base

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull

/**
 * This class represents the ID of a JSON-RPC request or response.
 * Since original specification allows both string and integer IDs,
 * this class is designed to handle both types.
 * It acts like a String|Int type.
 */
@Serializable(with = RpcIdSerializer::class)
sealed class RpcId {
	data class StringId(val value: String) : RpcId()
	data class IntId(val value: Long) : RpcId()
}

private object RpcIdSerializer : KSerializer<RpcId> {
	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("RpcId", PrimitiveKind.STRING)
	
	override fun deserialize(decoder: Decoder): RpcId {
		val jsonDecoder = decoder as? JsonDecoder
			?: throw IllegalStateException("This serializer can be used only with Json format")
		
		val element = jsonDecoder.decodeJsonElement()
		return when {
			element is JsonPrimitive && element.isString -> RpcId.StringId(element.content)
			element is JsonPrimitive && element.jsonPrimitive.longOrNull != null -> RpcId.IntId(element.jsonPrimitive.long)
			else -> throw IllegalStateException("Unsupported RpcId format: $element")
		}
	}
	
	override fun serialize(encoder: Encoder, value: RpcId) {
		when (value) {
			is RpcId.StringId -> encoder.encodeString(value.value)
			is RpcId.IntId -> encoder.encodeLong(value.value)
		}
	}
}