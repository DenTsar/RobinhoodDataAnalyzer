@file:UseSerializers(InstantAsStringSerializable::class)

package responses_approved.options

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import responses_approved.options.enums.Direction
import responses_approved.options.enums.Strategy
import java.time.Instant
import java.time.LocalDate

//Page of https://api.robinhood.com/options/aggregate_positions/?nonzero=True&strategy_code=7b4de5e2-5b73-42d0-be7e-b140081940e5_L1
//(Not a page of) https://api.robinhood.com/options/aggregate_positions/ad92f895-723f-4e07-8f8d-746a32b4b923/
//Page of https://api.robinhood.com/options/aggregate_positions/?chain_ids=1941ed52-7389-446b-b6fb-7c461f29c8be&nonzero=True
//Fun fact: putting any number after the L at the end doesn't give error, but no data in this case
//opposing_overlap_strategy_code is also a possible parameter
@Serializable
data class OptionAggregatePosition(
    val account: String,
    @SerialName("average_open_price")
    val averageOpenPrice: Double,
    val chain: String,
    @SerialName("created_at")
    val createdAt: Instant,
    val direction: Direction,
    val id: String,
    @SerialName("intraday_average_open_price")
    val intradayAverageOpenPrice: Double,
    @SerialName("intraday_direction")
    val intradayDirection: Direction,
    @SerialName("intraday_quantity")
    val intradayQuantity: Double,
    val legs: List<LegB>,
    val quantity: Double,
    val strategy: Strategy,
    @SerialName("strategy_code")
    val strategyCode: String,
    val symbol: String,
    @SerialName("trade_value_multiplier")
    val tradeValueMultiplier: Double,
    @SerialName("updated_at")
    val updatedAt: Instant
) {
    @Serializable
    data class LegB(
        @SerialName("expiration_date")
        @Serializable(with = DateAsStringSerializable::class)
        val expirationDate: LocalDate,
        val id: String,
        val option: String,
        @SerialName("option_type")
        val optionType: String,
        val position: String,
        @SerialName("position_type")
        val positionType: String,
        @SerialName("ratio_quantity")
        val ratioQuantity: Int,
        @SerialName("strike_price")
        val strikePrice: String
    )
}