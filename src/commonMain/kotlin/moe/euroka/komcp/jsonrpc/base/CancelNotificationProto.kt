package moe.euroka.komcp.jsonrpc.base

data class CancelNotificationProto(
	val requestId: RpcId,
	val reason: String?
)
