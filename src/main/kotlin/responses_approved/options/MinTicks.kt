import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinTicks(
    @SerialName("above_tick")
    val aboveTick: Double,
    @SerialName("below_tick")
    val belowTick: Double,
    @SerialName("cutoff_price")
    val cutoffPrice: Double
)