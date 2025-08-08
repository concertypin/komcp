package moe.euroka.komcp.jsonrpc.base

import kotlinx.serialization.Serializable

@Serializable
data class InitializeRequestProto(
	val protocolVersion: String, val capabilities: ClientCapabilitiesProto, val clientInfo: ImplementationProto
) : JsonRpcData

@Serializable
data class InitializeResponseProto(
	val protocolVersion: String,
	val capabilities: ServerCapabilitiesProto,
	val serverInfo: ImplementationProto,
	val instructions: String?
) : JsonRpcData

@Serializable
data class ContentListChangeableProto(val listChanged: Boolean?)


@Serializable
data class ClientCapabilitiesProto(
	val elicitation: AmbiguousObject?,
	val experimental: AmbiguousObject?,
	val roots: ContentListChangeableProto?,
	val sampling: AmbiguousObject?
)


@Serializable
data class ImplementationProto(val name: String, val title: String?, val version: String)


@Serializable
data class ServerCapabilitiesProto(
	val completion: AmbiguousObject?,
	val experimental: AmbiguousObject?,
	val logging: AmbiguousObject?,
	val prompts: ContentListChangeableProto?,
	val resources: ResourcesProto?,
	val tools: ContentListChangeableProto?
) {
	@Serializable
	data class ResourcesProto(val listChanged: Boolean?, val subscribe: Boolean?)
}
