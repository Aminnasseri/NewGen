package AminAmix.com.socialpersonality.model


import com.google.gson.annotations.SerializedName

data class Parameters(
    @SerializedName("E")
    val e: Int,
    @SerializedName("F")
    val f: Int,
    @SerializedName("I")
    val i: Int,
    @SerializedName("J")
    val j: Int,
    @SerializedName("N")
    val n: Int,
    @SerializedName("P")
    val p: Int,
    @SerializedName("S")
    val s: Int,
    @SerializedName("T")
    val t: Int
)