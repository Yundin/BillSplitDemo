package com.yundin.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yundin.designsystem.theme.BillSplitAppTheme
import java.math.BigDecimal

@Composable
fun Contact(
    name: String,
    debt: BigDecimal,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = name)
        val zeroDebt = debt.compareTo(BigDecimal.ZERO) == 0
        val debtText = when {
            zeroDebt -> "Doesn't owes you anything"
            else -> "Owes you ${debt.toPlainString()}"
        }
        Text(text = debtText, color = Color.Gray)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactPreview() {
    BillSplitAppTheme {
        Surface {
            Contact(name = "Name", debt = BigDecimal(100))
        }
    }
}

@Preview
@Composable
fun ContactNoDebtPreview() {
    BillSplitAppTheme {
        Surface {
            Contact(name = "Name", debt = BigDecimal.ZERO)
        }
    }
}
