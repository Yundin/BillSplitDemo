package com.yundin.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yundin.designsystem.theme.BillSplitAppTheme
import java.math.BigDecimal

@Composable
fun ContactItem(
    name: String,
    debt: BigDecimal?,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
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
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Filled.Close,
            contentDescription = stringResource(R.string.remove_contact_btn_desc),
            modifier = Modifier.clickable(onClick = onRemoveClick)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactPreview() {
    BillSplitAppTheme {
        Surface {
            ContactItem(name = "Name", debt = BigDecimal(100), {})
        }
    }
}

@Preview
@Composable
fun ContactNoDebtPreview() {
    BillSplitAppTheme {
        Surface {
            ContactItem(name = "Name", debt = BigDecimal.ZERO, {})
        }
    }
}
