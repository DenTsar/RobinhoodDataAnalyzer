package responses_approved.options

import MinTicks
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import responses_approved.options.enums.OptionState
import responses_approved.options.enums.OptionType
import responses_approved.options.enums.Tradability

//Page of https://api.robinhood.com/options/instruments/?chain_id=1941ed52-7389-446b-b6fb-7c461f29c8be&expiration_dates=2021-10-15&state=active&type=call
//https://api.robinhood.com/options/instruments/7c3afc04-a02e-418f-bf88-ef4c84b0047d/
@Serializable
data class OptionInstrument(
    @SerialName("chain_id")
    val chainId: String,
    @SerialName("chain_symbol")
    val chainSymbol: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("expiration_date")
    val expirationDate: LocalDate,
    val id: String,
    @SerialName("issue_date")
    val issueDate: LocalDate,
    @SerialName("long_strategy_code")
    val longStrategyCode: String,
    @SerialName("min_ticks")
    val minTicks: MinTicks,
    @SerialName("rhs_tradability")
    val rhsTradability: Tradability,
    @SerialName("sellout_datetime")
    val selloutDatetime: Instant,
    @SerialName("short_strategy_code")
    val shortStrategyCode: String,
    val state: OptionState,
    @SerialName("strike_price")
    val strikePrice: Double,
    val tradability: Tradability,
    val type: OptionType,
    @SerialName("updated_at")
    val updatedAt: Instant,
    val url: String,
)