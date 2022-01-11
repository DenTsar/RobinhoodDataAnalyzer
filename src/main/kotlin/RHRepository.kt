import io.ktor.client.request.*
import responses_approved.general.Page
import responses_approved.options.OptionAggregatePosition
import responses_approved.options.OptionInstrument
import responses_approved.options.OptionOrder


class RHRepository{
    val GENERAL_URL = "https://api.robinhood.com"
    val CRYPTO_URL = "https://nummus.robinhood.com"

//    suspend fun getCryptoOrders(): Page<CryptoOrder> {
//        return client.get("$GENERAL_URL/orders")
//    }
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
//    suspend fun getOptionQuote2() : String{
//        val response : HttpResponse = client.get("$GENERAL_URL/marketdata/options/?ids=7b4de5e2-5b73-42d0-be7e-b140081940e5")
//        return response.receive()
//    }

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