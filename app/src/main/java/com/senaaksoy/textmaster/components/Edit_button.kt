package com.senaaksoy.textmaster.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun myButton(@StringRes text: Int,
             onClickAction:()->Unit,
             modifier: Modifier=Modifier){
    Button(onClick = { onClickAction() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
,
                modifier = modifier.fillMaxWidth()
            .padding(16.dp)
            .background(brush =Brush.linearGradient(listOf(Color(0xFF000000),Color(0xFF0E4560))),
                shape = RoundedCornerShape(16.dp) ),
    ) {
       Text(text = stringResource(id = text))
    }
}
