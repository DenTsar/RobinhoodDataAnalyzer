import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import responses_approved.options.OptionOrder
import responses_approved.options.enums.Side
import responses_approved.options.enums.OrderState
import responses_approved.options.enums.PositionEffect
import kotlin.math.abs

fun main() {
    val rh = RHRepository()

    val optionOrders = runBlocking {
        rh.getOptionOrders().results
    }.filter { it.state==OrderState.FILLED }.filter { it.chainSymbol!="JG" }

    println(optionOrders.sumOf { abs(it.legs[0].executions[0].price - it.price) }*100)
//        if(it.chainSymbol=="SBUX")
//            println(Json.encodeToString(it))
//        if(it.legs[0].executions[0].price!=it.price) {
////            println(Json.encodeToString(it))
//            println(it.legs[0].side)
//            println(it.legs[0].executions[0].price - it.price)
//        }
//    }
    println(optionOrders.size)

    println(optionOrders.flatMap { it.legs }.size)

    optionOrders.forEach {
//        if(it.chainSymbol=="JG")
//            println(Json.encodeToString(it))
        it.legs.forEach { i ->
//            if(i.side==PositionEffect.OPEN && i.positionEffect!=PositionEffect.OPEN)
//                println("A"+it)
//            if(i.side==PositionEffect.CLOSE && i.positionEffect!=PositionEffect.CLOSE)
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

//    for(i in optionOrders){
//        if(i.legs.size!=1)
//            println(i.legs.size)
//    }

    val optionOrderMap = optionOrders.groupBy { it.legs[0].option.dropLast(1).split("/").last() }
        .mapValues { it.value.groupBy { i -> i.legs[0].positionEffect } }


    println(optionOrderMap.values.sumOf { (it[PositionEffect.OPEN]?.size ?: 0)+ (it[PositionEffect.CLOSE]?.size ?: 0)})

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
        val buysAve = averageCost(v[PositionEffect.OPEN])
        println("Average: $buysAve")

        println("Sells")
        val sellsAve = averageCost(v[PositionEffect.CLOSE])
        println("Average: $sellsAve")

        val profit = (sellsAve-buysAve)*100
        println("Profit: $profit")

        summary to profit
    }.sortedBy { it.second }

    println(tt)
    println(tt.sumOf { it.second })
//    for ((k,v) in optionOrderMap){
//        val option = optionInstruments[k] ?: return
//        with(option){
//            println("$chainSymbol $strikePrice $type $expirationDate")
//        }
//
//        println("Buys")
//        val buysAve = averageCost(v[PositionEffect.OPEN])
//        println("Average: $buysAve")
//
//        println("Sells")
//        val sellsAve = averageCost(v[PositionEffect.OPEN])
//        println("Average: $sellsAve")
//
//        println("Profit: ${(sellsAve-buysAve)*100}")
//    }
//    val curated = y.filter { it.value.size==2 && it.value.any { i -> i.legs[0].side == PositionEffect.OPEN } && i.value.any { it.legs[0].side == PositionEffect.CLOSE }}
}

fun <T> getDistinct(data: List<T>, method: T.() -> Any?): List<Any?> {
    return data.distinctBy { it.method() }.map { it.method() }
}

fun averageCost(orders: List<OptionOrder>?): Double {
    return orders?.let{ order ->
        order.sumOf {
            println("${it.legs[0].executions[0].price}+${it.quantity}")
            it.quantity*it.legs[0].executions[0].price
        }
    } ?: 0.0
}
