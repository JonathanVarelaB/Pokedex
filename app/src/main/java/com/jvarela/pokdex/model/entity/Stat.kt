package com.jvarela.pokdex.model.entity

import android.os.Parcel
import android.os.Parcelable

class FinalStat(val name: String, val value: String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<FinalStat> {
        override fun createFromParcel(parcel: Parcel): FinalStat {
            return FinalStat(parcel)
        }

        override fun newArray(size: Int): Array<FinalStat?> {
            return arrayOfNulls(size)
        }
    }

}