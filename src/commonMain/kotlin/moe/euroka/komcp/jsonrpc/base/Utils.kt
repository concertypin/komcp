/**
 * Utils.kt: provides type aliases, data classes, etc for JSON-RPC handling.
 */

package moe.euroka.komcp.jsonrpc.base

import kotlinx.serialization.Serializable


/**
 * Nothing to send, used for empty requests or responses.
 * Useful for cases where a param does not require any parameters, but param itself is required.
 */
@Serializable
data class EmptyObject(val empty: String? = null) : JsonRpcData

/**
 * This type is used to represent an ambiguous object in JSON-RPC.
 * It shouldn't be used if the type is known.
 */
typealias AmbiguousObject = @Serializable Map<Any, Any>