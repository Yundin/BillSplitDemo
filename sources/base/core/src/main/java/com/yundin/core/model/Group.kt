package com.yundin.core.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

data class Group(
    val id: Long,
    val title: String,
    val dateCreated: Date,
    val amountSpent: BigDecimal,
    val participantsCount: Int,
    val contacts: List<GroupContact>,
) {
    val debtPerPerson: BigDecimal = amountSpent.divide(
        participantsCount.toBigDecimal(),
        2,
        RoundingMode.HALF_UP
    )

    val debtLeft: BigDecimal = contacts.sumOf { contact ->
        if (contact.checked) {
            BigDecimal.ZERO
        } else {
            debtPerPerson
        }
    }
}

data class GroupContact(
    val id: Long,
    val name: String,
    val checked: Boolean
)
