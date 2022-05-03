package com.yundin.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@Composable
fun CheckableContactItem(
    name: String,
    debt: BigDecimal?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = name)
        if (debt != null) {
            val isZeroDebt = debt.compareTo(BigDecimal.ZERO) == 0
            val debtText = when {
                isZeroDebt -> stringResource(R.string.contact_no_debt_subtitle)
                else -> stringResource(R.string.contact_debt_subtitle_format, debt)
            }
            Text(text = debtText, color = Color.Gray)
        }
    }
}
