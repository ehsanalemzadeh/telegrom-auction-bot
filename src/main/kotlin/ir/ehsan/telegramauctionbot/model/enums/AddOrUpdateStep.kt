package ir.ehsan.telegramauctionbot.model.enums


enum class AddOrUpdateStep(override val message: String) : RequestStep {
    TYPE("لطفا نوع پیشنهاد را وارد کنید"), NAME("عنوان محصول را وارد کنید"), PRICE("قیمت پیشنهادی را وارد کنید"),
    DESCRIPTION
    ("توضیحات را وارد کنید.");

    companion object {
        private val vals: Array<AddOrUpdateStep> = entries.toTypedArray()
    }

    override fun next(): AddOrUpdateStep? {
        return if (isLast())
            null
        else
            vals[(this.ordinal + 1) % vals.size]
    }

    override fun isLast(): Boolean {
        return this.ordinal == vals.size - 1
    }
}