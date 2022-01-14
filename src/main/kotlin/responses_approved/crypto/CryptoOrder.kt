@file:UseSerializers(BigDecimalHumanReadableSerializer::class)

package responses_approved.crypto
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.serialization.kotlinx.bigdecimal.BigDecimalHumanReadableSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import responses_approved.options.enums.Side
import responses_approved.options.enums.TimeInForce
import responses_approved.options.enums.Type

@Serializable
data class CryptoOrder(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("average_price")
    val averagePrice: BigDecimal?,
    @SerialName("cancel_url")
    val cancelUrl: String?,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("cumulative_quantity")
    val cumulativeQuantity: BigDecimal,
    @SerialName("currency_pair_id")
    val currencyPairId: String,
    @SerialName("entered_price")
    val enteredPrice: BigDecimal,
    val executions: List<CryptoExecution>,
    val id: String,
    @SerialName("initiator_id")
    val initiatorId: String?,
    @SerialName("initiator_type")
    val initiatorType: String?,
    @SerialName("last_transaction_at")
    val lastTransactionAt: Instant,
    val price: BigDecimal,
    val quantity: BigDecimal,
    @SerialName("ref_id")
    val refId: String,
    @SerialName("rounded_executed_notional")
    val roundedExecutedNotional: BigDecimal,
    val side: Side,
    val state: CryptoOrderState,//Different from OrderState due to spelling of canceled
    @SerialName("time_in_force")
    val timeInForce: TimeInForce,//only observed -> TILL_CANCELLED
    val type: Type,
    @SerialName("updated_at")
    val updatedAt: Instant
){
    @Serializable
    data class CryptoExecution(
        @SerialName("effective_price")
        val effectivePrice: BigDecimal,
        val id: String,
        val quantity: BigDecimal,
        val timestamp: Instant
    )
}