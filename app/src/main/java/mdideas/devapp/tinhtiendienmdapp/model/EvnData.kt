package mdideas.devapp.tinhtiendienmdapp.model

import android.os.Parcel
import android.os.Parcelable

data class EvnData(
    val typedCustomer: String? = "",
    val typedPrice: String? = "",
    val electricPrice: Int? = 0,
    val electricOutput: Int? = 0,
    val electricAmount: Int? = 0,
    val electricUsing: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(typedCustomer)
        parcel.writeString(typedPrice)
        parcel.writeValue(electricPrice)
        parcel.writeValue(electricOutput)
        parcel.writeValue(electricAmount)
        parcel.writeString(electricUsing)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EvnData> {
        override fun createFromParcel(parcel: Parcel): EvnData {
            return EvnData(parcel)
        }

        override fun newArray(size: Int): Array<EvnData?> {
            return arrayOfNulls(size)
        }
    }
}