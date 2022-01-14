package responses_approved.options

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import responses_approved.options.enums.*


@Serializable
data class OptionOrder(
    @SerialName("cancel_url")
    val cancelUrl: String?,
    @SerialName("canceled_quantity")
    val canceledQuantity: Double,
    @SerialName("chain_id")
    val chainId: String,
    @SerialName("chain_symbol")
    val chainSymbol: String,
    @SerialName("closing_strategy")
    val closingStrategy: Strategy?,
    @SerialName("created_at")
    val createdAt: Instant,
    val direction: Direction,
    @SerialName("form_source")
    val formSource: FormSource?,
    val id: String,
    val legs: List<LegA>,
    @SerialName("opening_strategy")
    val openingStrategy: Strategy?,
    @SerialName("pending_quantity")
    val pendingQuantity: Double,
    val premium: Double,
    val price: Double,
    @SerialName("processed_premium")
    val processedPremium: Double,
    @SerialName("processed_quantity")
    val processedQuantity: Double,
    val quantity: Double,
    @SerialName("ref_id")
    val refId: String,
    @SerialName("response_category")
    val responseCategory: String?,
    val state: OrderState,
    @SerialName("stop_price")
    val stopPrice: String?,
    @SerialName("time_in_force")
    val timeInForce: TimeInForce,
    val trigger: Trigger,
    val type: Type,
    @SerialName("updated_at")
    val updatedAt: Instant,
) {
    @Serializable
    data class LegA(
        val executions: List<Execution>,
        @SerialName("expiration_date")
        val expirationDate: LocalDate,
        val id: String,
        @SerialName("long_strategy_code")
        val longStrategyCode: String,
        val option: String,
        @SerialName("option_type")
        val optionType: OptionType,
        @SerialName("position_effect")
        val positionEffect: PositionEffect,
        @SerialName("ratio_quantity")
        val ratioQuantity: Int,
        @SerialName("short_strategy_code")
        val shortStrategyCode: String,
        val side: Side,
        @SerialName("strike_price")
        val strikePrice: Double,
    ){
        @Serializable
        data class Execution(
            val id: String,
            val price: Double,
            val quantity: Double,
            @SerialName("settlement_date")
            val settlementDate: LocalDate,
            val timestamp: Instant,
        )
    }
}
