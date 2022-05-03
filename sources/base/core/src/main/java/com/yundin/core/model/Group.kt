package com.yundin.core.model

import java.math.BigDecimal
import java.util.*

data class Group(
    val id: Long,
    val title: String,
    val dateCreated: Date,
    val amountSpent: BigDecimal,
    val contacts: List<GroupContact>,
)

data class GroupContact(
    val id: Long,
    val name: String,
    val checked: Boolean
)
