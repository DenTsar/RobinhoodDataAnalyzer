import kotlinx.serialization.*

@Serializable
data class CryptoExecution(
    @SerialName("effective_price")
    val effectivePrice: String,
    val id: String,
    val quantity: String,
    val timestamp: String
)