package com.example.expensetracker.model

import android.os.Parcel
import android.os.Parcelable

data class userModel (
    var userId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var phoneNumber: String = "",
    var email: String = "",
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(address)
        parcel.writeString(phoneNumber)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<userModel> {
        override fun createFromParcel(parcel: Parcel): userModel {
            return userModel(parcel)
        }

        override fun newArray(size: Int): Array<userModel?> {
            return arrayOfNulls(size)
        }
    }
}