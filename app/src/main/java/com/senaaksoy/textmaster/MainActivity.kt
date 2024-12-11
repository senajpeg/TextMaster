package com.senaaksoy.textmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.senaaksoy.textmaster.navigation.TextNavigation
import com.senaaksoy.textmaster.textScreens.HomeScreen
import com.senaaksoy.textmaster.ui.theme.TextMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TextMasterTheme {
               TextNavigation()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    TextMasterTheme {
       TextNavigation()
    }
}