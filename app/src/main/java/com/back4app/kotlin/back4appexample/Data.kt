package com.back4app.kotlin.back4appexample

import java.util.*

class Data (var objectId: String? = null, var itemName:String? = null, var additionalInformation: String? = null, var dateCommitment: Date? = null, var isAvailable: Boolean? = null ) {
    override fun toString(): String {
        return "Data(objectId=$objectId, itemName=$itemName, additionalInformation=$additionalInformation, dateCommitment=$dateCommitment, isAvailable=$isAvailable)"
    }
}