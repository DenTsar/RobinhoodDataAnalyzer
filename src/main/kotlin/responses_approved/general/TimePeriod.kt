package responses_approved.general

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimePeriod {
    @SerialName("15second")
    FIFTEEN_SEC,
    @SerialName("5minute")
    FIVE_MIN,
    @SerialName("10minute")
    TEN_MIN,
    @SerialName("30minute")
    THIRTY_MIN,
    @SerialName("hour")
    ONE_HOUR,
    @SerialName("day")
    ONE_DAY,
    @SerialName("week")
    ONE_WEEK,
    @SerialName("month")
    ONE_MONTH,
    @SerialName("3month")
    THREE_MONTH,
    @SerialName("6month")
    SIX_MONTH,
    @SerialName("year")
    ONE_YEAR,
    @SerialName("5year")
    FIVE_YEAR,
    @SerialName("10year")
    TEN_YEAR,
    @SerialName("20year")
    TWENTY_YEAR,
    @SerialName("50year")
    FIFTY_YEAR,
}