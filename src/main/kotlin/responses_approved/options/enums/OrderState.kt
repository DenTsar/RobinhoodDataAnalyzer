package responses_approved.options.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrderState{
    @SerialName("cancelled")
    CANCELLED,
    @SerialName("confirmed")
    CONFIRMED,
    @SerialName("filled")
    FILLED,
    @SerialName("new")
    NEW,
    @SerialName("queued")
    QUEUED,
    @SerialName("rejected")
    REJECTED,
    @SerialName("unconfirmed")
    UNCONFIRMED,
    @SerialName("partially_filled")
    PARTIALLY_FILLED,
    @SerialName("pending_cancelled")
    PENDING_CANCELLED,
}