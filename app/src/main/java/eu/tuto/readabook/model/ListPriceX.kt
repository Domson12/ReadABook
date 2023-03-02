package eu.tuto.readabook.model


import com.google.gson.annotations.SerializedName

data class ListPriceX(
    @SerializedName("amountInMicros")
    val amountInMicros: Int,
    @SerializedName("currencyCode")
    val currencyCode: String
)