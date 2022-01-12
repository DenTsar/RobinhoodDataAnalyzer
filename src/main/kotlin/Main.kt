import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import responses_approved.options.OptionOrder
import responses_approved.options.enums.Side
import responses_approved.options.enums.OrderState
import kotlin.math.abs

fun main() {
    val rh = RHRepository()

    val hist = runBlocking { rh.getOptionQuote2() }
    println(hist)

    val optionOrders = runBlocking { rh.getOptionOrders() }.results
        .filter { it.state==OrderState.FILLED }
//        .filter { it.chainSymbol!="JG" }

    println("Money made from beyond limit orders: "+optionOrders.sumOf { abs(it.legs[0].executions[0].price - it.price) }*100)

    println(optionOrders.size)

    println(optionOrders.flatMap { it.legs }.size)

    optionOrders.forEachIndexed { i, v ->
        if(i==100)
            println(Json.encodeToString(v))
    }

//    listOf(OptionOrder::state,OptionOrder::timeInForce,OptionOrder::trigger,OptionOrder::type,OptionOrder::timeInForce,OptionOrder::responseCategory,
//        OptionOrder::openingStrategy,OptionOrder::closingStrategy,OptionOrder::formSource,)
//        .forEach{ println(it.name+getDistinct(x,it)) }
//
//    listOf(responses_approved.options.Leg::positionEffect,responses_approved.options.Leg::side,responses_approved.options.Leg::optionType).forEach { i -> println(i.name+getDistinct(x.flatMap { it.legs },i)) }

//    val q = getDistinct(optionOrders.flatMap{ it.legs },responses_approved.options.Leg::option)
//    println(q.size)
//
//    val qq = getDistinct(optionOrders,OptionOrder::chainId)
//    println(qq.size)

    val optionPositions = runBlocking { rh.getOptionAggregatePosition() }.results
        .flatMap { it.legs }.map { idFromURL(it.option) }

    println(optionPositions[0])

    val optionOrdersMap = optionOrders.flatMap { it.legs }.groupBy { idFromURL(it.option) }
        .mapValues { i -> i.value.groupBy { it.side } }.filterKeys { !optionPositions.contains(it) }

    println(optionOrdersMap.values.sumOf { (it[Side.BUY]?.size ?: 0)+ (it[Side.SELL]?.size ?: 0)})

    val optionProfits = version3(rh,optionOrdersMap)


//    listOf(OptionInstrument::rhsTradability,OptionInstrument::tradability,OptionInstrument::type,OptionInstrument::state)
//        .forEach{ println(it.name+getDistinct(optionInstruments.values.toList(),it)) }

    println(optionProfits)
    println(optionProfits.sumOf { it.second })
    println(optionProfits.size)
    println("Lost total: ${optionProfits.filter { it.second < 0 }.size} Gained: ${optionProfits.filter { it.second > 0 }.size}")
    val companyProfits = optionProfits.groupBy { it.first.split(" ")[0] }
        .map { (key, value) -> key to value.sumOf { it.second } }.sortedBy { it.second }
    println(companyProfits)
    println(companyProfits.size)
    println("Lost total: ${companyProfits.filter { it.second < 0 }.size} Gained: ${companyProfits.filter { it.second > 0 }.size}")

}

fun <T> getDistinct(data: List<T>, method: T.() -> Any?): List<Any?> =
    data.distinctBy { it.method() }.map { it.method() }

fun totalAmount(legs: List<OptionOrder.LegA>?): Double {
//    return legs?.let{ i ->
//        i.flatMap { it.executions }.sumOf {
//            println("${it.price}x${it.quantity}")
//            it.quantity*it.price
//        }
//    } ?: 0.0
    return legs?.flatMap { it.executions }
        ?.sumOf {
            println("${it.price}x${it.quantity}")
            it.quantity*it.price
        } ?: 0.0
}

fun idFromURL(url: String): String = url.dropLast(1).split("/").last()

fun version1(rh: RHRepository, optionOrdersMap: Map<String, Map<Side, List<OptionOrder.LegA>>>){
    val optionInstruments = optionOrdersMap.keys.chunked(64).map {
        runBlocking { rh.getOptionInstruments(it) }.results
    }.flatten().associateBy { it.id }

    val optionProfits = optionOrdersMap.map { (k,v) ->
        val option = optionInstruments[k]
        if(option==null){
            println("disaster")
            return
        }

        val summary = with(option){
            "$chainSymbol $strikePrice $type $expirationDate"
        }
        println(summary)

        println("Buys")
        val buysTotal = totalAmount(v[Side.BUY])
        println("Total: $buysTotal")

        println("Sells")
        val sellsTotal = totalAmount(v[Side.SELL])
        println("Total: $sellsTotal")

        val profit = (sellsTotal-buysTotal)*100
        println("Profit: $profit")

        summary to profit
    }.sortedBy { it.second }
}

fun version2(rh: RHRepository, optionOrdersMap: Map<String, Map<Side, List<OptionOrder.LegA>>>){
    val optionInstruments = optionOrdersMap.keys.chunked(64).map {
        runBlocking { rh.getOptionInstruments(it) }.results
    }.flatten().associateBy { it.id }

    val k = optionInstruments.keys.map {
        optionInstruments.getValue(it) to optionOrdersMap.getValue(it)
    }

    val optionProfits = k.map { (option,v) ->
        val summary = with(option){
            "$chainSymbol $strikePrice $type $expirationDate"
        }
        println(summary)

        println("Buys")
        val buysTotal = totalAmount(v[Side.BUY])
        println("Total: $buysTotal")

        println("Sells")
        val sellsTotal = totalAmount(v[Side.SELL])
        println("Total: $sellsTotal")

        val profit = (sellsTotal-buysTotal)*100
        println("Profit: $profit")

        summary to profit
    }.sortedBy { it.second }
}

fun version3(rh: RHRepository, optionOrdersMap: Map<String, Map<Side, List<OptionOrder.LegA>>>): List<Pair<String, Double>> {
    val optionInstruments = optionOrdersMap.keys.chunked(64).map {
        runBlocking { rh.getOptionInstruments(it) }.results
    }.flatten()

    val master = optionInstruments.map { it to optionOrdersMap.getValue(it.id) }

    return master.map { (option,legs) ->
        val summary = with(option){
            "$chainSymbol $strikePrice $type $expirationDate"
        }
        println(summary)

        println("Buys")
        val buysTotal = totalAmount(legs[Side.BUY])
        println("Total: $buysTotal")

        println("Sells")
        val sellsTotal = totalAmount(legs[Side.SELL])
        println("Total: $sellsTotal")

        val profit = (sellsTotal-buysTotal)*100
        println("Profit: $profit")

        summary to profit
    }.sortedBy { it.second }
}