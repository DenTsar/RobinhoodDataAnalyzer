import kotlinx.coroutines.runBlocking
import responses_approved.options.OptionOrder
import responses_approved.options.enums.Side
import responses_approved.options.enums.OrderState
import kotlin.math.abs

fun main() {
    val rh = RHRepository()

    val optionOrders = runBlocking {
        rh.getOptionOrders().results
    }.filter { it.state==OrderState.FILLED }

    println(optionOrders.sumOf { abs(it.legs[0].executions[0].price - it.price) }*100)

    println(optionOrders.size)

    println(optionOrders.flatMap { it.legs }.size)

    optionOrders.forEach {
//        if(it.chainSymbol=="JG")
//            println(Json.encodeToString(it))
        it.legs.forEach { i ->
//            if(i.side==Side.BUY && i.positionEffect!=Side.BUY)
//                println("A"+it)
//            if(i.side==Side.SELL && i.positionEffect!=Side.SELL)
//                println("B"+it)
        }
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
        .flatMap { it.legs }.map { it.option.dropLast(1).split("/").last() }

    val optionOrderMap = optionOrders.flatMap { it.legs }.groupBy { it.option.dropLast(1).split("/").last() }
        .mapValues { it.value.groupBy { i -> i.side } }.filterKeys { !optionPositions.contains(it) }


    println(optionOrderMap.values.sumOf { (it[Side.BUY]?.size ?: 0)+ (it[Side.SELL]?.size ?: 0)})

    val optionInstruments = optionOrderMap.keys.chunked(64).map {
        runBlocking { rh.getOptionInstruments(it) }.results
    }.flatten().associateBy { it.id }

//    listOf(OptionInstrument::rhsTradability,OptionInstrument::tradability,OptionInstrument::type,OptionInstrument::state)
//        .forEach{ println(it.name+getDistinct(optionInstruments.values.toList(),it)) }

    val tt = optionOrderMap.entries.map {
        val v = it.value
        val k = it.key

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
        val buysAve = totalAmount(v[Side.BUY])
        println("Average: $buysAve")

        println("Sells")
        val sellsAve = totalAmount(v[Side.SELL])
        println("Average: $sellsAve")

        val profit = (sellsAve-buysAve)*100
        println("Profit: $profit")

        summary to profit
    }.sortedBy { it.second }

    println(tt)
    println(tt.sumOf { it.second })
    println(tt.size)
    println("Lost total: ${tt.filter { it.second < 0 }.size} Gained: ${tt.filter { it.second > 0 }.size}")
    val zz = tt.groupBy { (first) -> first.split(" ")[0] }
        .map { (key, value) -> key to value.sumOf { it.second } }.sortedBy { it.second }
    println(zz)
    println(zz.size)
    println("Lost total: ${zz.filter { it.second < 0 }.size} Gained: ${zz.filter { it.second > 0 }.size}")

}

fun <T> getDistinct(data: List<T>, method: T.() -> Any?): List<Any?> {
    return data.distinctBy { it.method() }.map { it.method() }
}

fun totalAmount(orders: List<OptionOrder.LegA>?): Double {
    return orders?.let{ order ->
        order.sumOf { (executions) ->
            println("${executions[0].price}+${executions[0].quantity}")
            executions.sumOf { it.quantity*it.price }
        }
    } ?: 0.0
}