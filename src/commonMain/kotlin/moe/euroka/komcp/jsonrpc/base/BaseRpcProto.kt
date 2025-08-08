@file:Suppress("unused")

package moe.euroka.komcp.jsonrpc.base

import kotlinx.serialization.Serializable

/**
 * Marker interface for JSON-RPC data.
 * This interface is used to indicate that a class can be serialized as JSON-RPC data.
 * It is typically used as a generic for request and response parameters in JSON-RPC messages.
 *
 * @see [BaseRequestProto]
 * @see [proto.json#/definitions/JSONRPCMessage]
 */
interface JsonRpcData

/**
 * The base interface for JSON-RPC protocol messages.
 *
 * @property jsonrpc The JSON-RPC protocol version (e.g., "2.0").
 * @property id The request identifier. Null for notifications.
 * @see [proto.json#/definitions/JSONRPCMessage]
 */
sealed interface BaseRpcProto {
	val jsonrpc: String
	val id: RpcId?
}

/**
 * JSON-RPC request data class.
 *
 * @param T The type of request parameters.
 * @property jsonrpc The JSON-RPC protocol version.
 * @property id The request identifier.
 * @property method The name of the method to invoke.
 * @property params The request parameters (nullable).
 * @see BaseResponseProtoSucceed
 * @see [proto.json#/definitions/JSONRPCRequest]
 */
@Serializable
data class BaseRequestProto<T>(
	override val jsonrpc: String,
	override val id: RpcId,
	val method: String,
	val params: T? = null
) : BaseRpcProto where T : @Serializable JsonRpcData
typealias BaseCallProto<T> = BaseRequestProto<T>

/**
 * JSON-RPC error response data class.
 *
 * @param T The type of error data.
 * @property jsonrpc The JSON-RPC protocol version.
 * @property id The request identifier.
 * @property error The error information.
 * @property result Always null.
 * @see BaseResponseProtoSucceed
 * @see [proto.json#/definitions/JSONRPCError]
 */
@Serializable
data class BaseResponseProtoErrored<T>(
	override val jsonrpc: String,
	override val id: RpcId,
	val error: RpcErrorProto<T>,
	val result: Nothing? = null
) : BaseRpcProto where T : @Serializable JsonRpcData {
	/**
	 * JSON-RPC error information data class.
	 *
	 * @param T The type of error data.
	 * @property code The error code.
	 * @property message The error message.
	 * @property data Additional error data (nullable).
	 * @see BaseResponseProtoErrored
	 * @see [proto.json#/definitions/JSONRPCError/properties/error]
	 */
	@Serializable
	data class RpcErrorProto<T>(
		val code: Int,
		val message: String,
		val data: T? = null
	)
}
typealias BaseAnswerProtoErrored<T> = BaseResponseProtoErrored<T>

/**
 * JSON-RPC success response data class.
 *
 * @param T The type of result data.
 * @property jsonrpc The JSON-RPC protocol version.
 * @property id The request identifier.
 * @property result The result data.
 * @property error Always null.
 * @see BaseResponseProtoErrored
 * @see [proto.json#/definitions/JSONRPCResponse]
 */
@Serializable
data class BaseResponseProtoSucceed<T>(
	override val jsonrpc: String,
	override val id: RpcId,
	val result: T,
	val error: Nothing? = null
) : BaseRpcProto where T : @Serializable JsonRpcData
typealias BaseAnswerProtoSucceed<T> = BaseResponseProtoSucceed<T>

/**
 * JSON-RPC notification data class.
 *
 * @param T The type of parameters.
 * @property jsonrpc The JSON-RPC protocol version.
 * @property method The notification method name.
 * @property id Always null (notifications do not include an ID).
 * @property params The parameters (nullable).
 * @see [proto.json#/definitions/JSONRPCNotification]
 */
@Serializable
data class BaseNotificationProto<T>(
	override val jsonrpc: String,
	val method: String,
	override val id: Nothing? = null,
	val params: T? = null
) : BaseRpcProto where T : @Serializable JsonRpcData


interface JsonRpcNotificationData {
	@Suppress("PropertyName")
	val _method: String
}
