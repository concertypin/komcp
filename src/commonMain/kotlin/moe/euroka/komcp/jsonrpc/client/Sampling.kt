package moe.euroka.komcp.jsonrpc.client

import kotlinx.serialization.Serializable
import moe.euroka.komcp.jsonrpc.base.JsonRpcData

@Serializable
data class SamplingCallProto(
	val messages: List<SamplingMessageProto>,
) : JsonRpcData
