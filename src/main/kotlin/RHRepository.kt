import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import responses_approved.crypto.CryptoOrder
import responses_approved.general.Page
import responses_approved.options.OptionAggregatePosition
import responses_approved.options.OptionInstrument
import responses_approved.options.OptionOrder


class RHRepository{
    val GENERAL_URL = "https://api.robinhood.com"
    val CRYPTO_URL = "https://nummus.robinhood.com"

    suspend fun getCryptoOrders(cursor: String? = null): Page<CryptoOrder> {
        return client.get("$CRYPTO_URL/orders/"){
//            parameter("page_size",page_size)
            parameter("cursor",cursor)
        }
    }
//    suspend fun getHistoricalCryptoData(id: String, bounds : String, interval : String, span : String) : HistoricalCrypto{
//        return client.get("$GENERAL_URL/marketdata/forex/historicals/$id/?bounds=$bounds&interval=$interval&span=$span")
//    }
//    suspend fun getHistoricalPortfolioData(id: String, bounds : String, interval : String, span : String) : HistoricalPortfolio{
//        return client.get("$GENERAL_URL/portfolios/historicals/830462008/?bounds=$bounds&interval=$interval&span=d$span")
//    }
//    suspend fun getAccountEquities() : AccountEquities{
//        return client.get("https://bonfire.robinhood.com/phoenix/accounts/unified")
//    }
//
//    suspend fun getOptionQuote() : Page<OptionQuoteOrOptionMarketData> {
//        return client.get("$GENERAL_URL/marketdata/options/?ids=7b4de5e2-5b73-42d0-be7e-b140081940e5")
//    }
//
    suspend fun getOptionQuote2() : String{
        return client.get<HttpResponse>(
            "$GENERAL_URL/marketdata/options/strategy/historicals/?bounds=regular&ids=a4653aa9-b9da-44cd-9d20-6b5b8a5e286f&interval=30minute&ratios=1&span=all&start=2021-11-06T15%3A00%3A00.000Z&types=long&end=2021-12-13T15%3A00%3A00.000Z"
        ).receive()
//        return response.toString()//a4653aa9-b9da-44cd-9d20-6b5b8a5e286f
    }

    suspend fun getOptionOrders(page_size: Int = 1000, chain_ids: List<String>? = null): Page<OptionOrder> {
        return client.get("$GENERAL_URL/options/orders/"){
            parameter("page_size",page_size)
            parameter("chain_ids",chain_ids?.joinToString(","))
//            parameter("states",states?.joinToString())
        }
    }

    suspend fun getOptionInstrument(url : String) : OptionInstrument{
        return client.get(url)
    }

    suspend fun getOptionInstruments(ids: List<String>? = null): Page<OptionInstrument>{
        if(ids!=null && ids.size>64)
            return Page(null,null, emptyList())
        return client.get("$GENERAL_URL/options/instruments/"){
            parameter("ids",ids?.joinToString(","))
        }
    }

    suspend fun getOptionAggregatePosition(nonzero: Boolean = true): Page<OptionAggregatePosition>{
        return client.get("https://api.robinhood.com/options/aggregate_positions/"){
            parameter("nonzero",nonzero.toString().replaceFirstChar(Char::uppercase))
        }
    }

}