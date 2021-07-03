package com.jvarela.pokdex.model

import android.os.Parcel
import android.os.Parcelable

class Stat(val name: String, val value: String): Parcelable {

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

    companion object CREATOR : Parcelable.Creator<Stat> {
        override fun createFromParcel(parcel: Parcel): Stat {
            return Stat(parcel)
        }

        override fun newArray(size: Int): Array<Stat?> {
            return arrayOfNulls(size)
        }
    }


}