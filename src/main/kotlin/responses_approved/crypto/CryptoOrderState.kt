package responses_approved.crypto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CryptoOrderState{
    @SerialName("canceled")
    CANCELLED,
    @SerialName("confirmed")
    CONFIRMED,
    @SerialName("filled")
    FILLED,
    @SerialName("rejected")
    REJECTED,
}