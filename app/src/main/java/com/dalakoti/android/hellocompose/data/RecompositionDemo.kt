package com.dalakoti.android.hellocompose.data

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private const val TAG = "RecompositionDemo"

data class Contact(val name: String, val number: String)

@Composable
fun ContactDetails(
    contact: Contact
) {
    Log.d(TAG, "ContactDetails recompose")
    Text(text = contact.name)
    Text(
        text = contact.number, modifier = Modifier.padding(
            start = 4.dp,
        )
    )
}

@Composable
fun ToggleButton(selected: Boolean, onToggled: () -> Unit = {}) {
    Log.d(TAG, "ToggleButton recomposed")
    Switch(
        checked = selected,
        onCheckedChange = {
            onToggled()
        },
    )
}

@Composable
fun ContactRow(contact: Contact, modifier: Modifier = Modifier) {
    Log.d(TAG, "ContactRow recomposed ")
    var selected by remember { mutableStateOf(false) }
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ContactDetails(contact)
        ToggleButton(selected, onToggled = { selected = !selected })
    }
}

@Composable
fun RecompositionDemo() {
    Log.d(TAG, "RecompositionDemo recomposed ")
    ContactRow(
        contact = Contact(
            name = "Saurabh Dalakoti",
            number = "9643957241",
        ),
        modifier = Modifier
            .padding(
                10.dp,
            )
            .background(
                color = Color.Green,
                shape = RoundedCornerShape(10.dp),
            ).padding(
                4.dp,
            ),
    )
}
