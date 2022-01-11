package responses_approved.options.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimeInForce {
    @SerialName("gtc")
    TILL_CANCELLED,
    @SerialName("gfd")
    TILL_DAY,
}

@Serializable
enum class FormSource {
    @SerialName("aggregate_position_detail")
    AGG_POS_DETAIL,
    @SerialName("option_chain")
    OPTION_CHAIN,
    @SerialName("order_detail")
    ORDER_TAIL,
}

@Serializable
enum class Strategy {
    @SerialName("custom")
    CUSTOM,
    @SerialName("long_call")
    LONG_CALL,
    @SerialName("long_put")
    LONG_PUT,
    @SerialName("short_call")
    SHORT_CALL,
    @SerialName("short_put")
    SHORT_PUT,
}

@Serializable
enum class Trigger {
    @SerialName("immediate")
    IMMEDIATE,
    @SerialName("stop")
    STOP,
}

@Serializable
enum class Type {
    @SerialName("limit")
    LIMIT,
}

@Serializable
enum class ResponseCategory {
    @SerialName("end_of_day")
    END_OF_DAY,
}

@Serializable
enum class Direction {
    @SerialName("credit")
    CREDIT,
    @SerialName("debit")
    DEBIT,
}

@Serializable
enum class PositionEffect {
    @SerialName("close")
    CLOSE,
    @SerialName("open")
    OPEN,
}

@Serializable
enum class Side {
    @SerialName("buy")
    BUY,
    @SerialName("sell")
    SELL,
}

@Serializable
enum class OptionType {
    @SerialName("call")
    CALL,
    @SerialName("put")
    PUT,
}

@Serializable
enum class Tradability {
    @SerialName("tradable")
    TRADABLE,
    @SerialName("untradable")
    UNTRADABLE,
}

@Serializable
enum class OptionState {
    @SerialName("active")
    ACTIVE,
    @SerialName("expired")
    EXPIRED,
}