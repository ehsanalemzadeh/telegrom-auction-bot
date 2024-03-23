package ir.ehsan.telegramauctionbot.model.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class OfferType(@JsonValue val title: String) {
    BUY("خرید"), SELL("فروش"), EXCHANGE("تعویض");

    companion object {
        fun value(value: String): OfferType {
            try {
                return OfferType.valueOf(value)
            } catch (e: IllegalArgumentException) {
                throw RuntimeException("نوع پیشنهاد وارد شده صحیح نیست. لطفا نوع پیشنهاد را مجددا وارد کنید")
            }
        }
    }
}