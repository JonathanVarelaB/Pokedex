package com.jvarela.pokdex.model

import android.os.Parcel
import android.os.Parcelable

class Pokemon(val id: String, val imageUrl: String, val name: String, val height: String, val weight: String, val abilities: List<String>, val stats: List<Stat>, var favorite: Boolean, val viewed: Boolean) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createTypedArrayList(Stat) ?: emptyList(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeString(height)
        parcel.writeString(weight)
        parcel.writeStringList(abilities)
        parcel.writeTypedList(stats)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeByte(if (viewed) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }


}