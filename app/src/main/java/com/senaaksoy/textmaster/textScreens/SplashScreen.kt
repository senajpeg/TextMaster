package com.senaaksoy.textmaster.textScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.senaaksoy.textmaster.R
import com.senaaksoy.textmaster.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = Unit) {
        delay(4000)
        navController.navigate(Screens.HOMESCREEN.route)
    }
    Box(modifier = Modifier.fillMaxSize()
        .background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF000000),
                Color(0xFF0E4560)
            )
        )
    )){
            Icon(
                painter = painterResource(id = R.drawable.ai_text_generation),
                contentDescription =null,
                modifier = Modifier.size(120.dp).align(alignment = Alignment.Center),
                tint = Color(0xFF9C27B0)
            )
            Text(
                text = stringResource(id = R.string.made_by_senaaksoy),
                fontStyle = FontStyle.Italic,
                fontSize = 19.sp,
                modifier = Modifier.padding(end=16.dp, bottom = 64.dp).align(alignment =Alignment.BottomEnd),
                fontWeight = FontWeight.Normal,
                color = Color(0xFF9C27B0),
            )

    }
}
