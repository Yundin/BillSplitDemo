package com.yundin.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yundin.designsystem.theme.BillSplitAppTheme
import java.math.BigDecimal

@Composable
fun Group(
    title: String,
    overallDebt: BigDecimal,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val isZeroDebt = overallDebt.compareTo(BigDecimal.ZERO) == 0
        val titleAlpha = if (isZeroDebt) 0.5f else 1f
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.alpha(titleAlpha)
        )

        val debtText = when {
            isZeroDebt -> stringResource(R.string.group_no_debt_subtitle)
            else -> stringResource(R.string.group_debt_subtitle_format, overallDebt)
        }
        Text(text = debtText, color = Color.Gray)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GroupPreview() {
    BillSplitAppTheme {
        Surface {
            Group(title = "Title", overallDebt = BigDecimal(100))
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GroupNoDebtPreview() {
    BillSplitAppTheme {
        Surface {
            Group(title = "Title", overallDebt = BigDecimal.ZERO)
        }
    }
}
