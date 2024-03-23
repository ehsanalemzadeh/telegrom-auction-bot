package ir.ehsan.telegramauctionbot.model.enums

interface RequestStep {

    val message: String
    fun next(): RequestStep?
    fun isLast(): Boolean
}