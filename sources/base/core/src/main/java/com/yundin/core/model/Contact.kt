package com.yundin.core.model

import java.math.BigDecimal

data class Contact(
    val id: Long,
    val name: String,
    val owesOverall: BigDecimal
)
