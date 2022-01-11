package responses_approved.options
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//Page of https://api.robinhood.com/marketdata/options/?ids=7b4de5e2-5b73-42d0-be7e-b140081940e5
@Serializable
data class OptionQuoteOrOptionMarketData(
    @SerialName("adjusted_mark_price")
    val adjustedMarkPrice: String,
    @SerialName("adjusted_mark_price_round_down")
    val adjustedMarkPriceRoundDown: String,
    @SerialName("ask_price")
    val askPrice: String,
    @SerialName("ask_size")
    val askSize: Int,
    @SerialName("bid_price")
    val bidPrice: String,
    @SerialName("bid_size")
    val bidSize: Int,
    @SerialName("break_even_price")
    val breakEvenPrice: String,
    @SerialName("chance_of_profit_long")
    val chanceOfProfitLong: String?,
    @SerialName("chance_of_profit_short")
    val chanceOfProfitShort: String?,
    val delta: String?,
    val gamma: String?,
    @SerialName("high_fill_rate_buy_price")
    val highFillRateBuyPrice: String,
    @SerialName("high_fill_rate_sell_price")
    val highFillRateSellPrice: String,
    @SerialName("high_price")
    val highPrice: String?,
    @SerialName("implied_volatility")
    val impliedVolatility: String?,
    val instrument: String,
    @SerialName("instrument_id")
    val instrumentId: String,
    @SerialName("last_trade_price")
    val lastTradePrice: String,
    @SerialName("last_trade_size")
    val lastTradeSize: Int,
    @SerialName("low_fill_rate_buy_price")
    val lowFillRateBuyPrice: String,
    @SerialName("low_fill_rate_sell_price")
    val lowFillRateSellPrice: String,
    @SerialName("low_price")
    val lowPrice: String?,
    @SerialName("mark_price")
    val markPrice: String,
    @SerialName("occ_symbol")
    val occSymbol: String,
    @SerialName("open_interest")
    val openInterest: Int,
    @SerialName("previous_close_date")
    val previousCloseDate: String,
    @SerialName("previous_close_price")
    val previousClosePrice: String,
    val rho: String?,
    val symbol: String,
    val theta: String?,
    val vega: String?,
    val volume: Int
)