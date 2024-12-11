package com.senaaksoy.textmaster.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyButton(@StringRes text: Int,
             onClickAction:()->Unit,
             modifier: Modifier=Modifier,
             @DrawableRes iconId: Int? = null){
    Button(onClick = { onClickAction() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(Color(0xFF000000), Color(0xFF0E4560))),
                        shape = RoundedCornerShape(16.dp)
                    ),
    ) {
        Row {
            Text(text = stringResource(id = text),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
                )
            iconId?.let {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null
                )
        }
    }
}}


