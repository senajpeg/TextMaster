package com.senaaksoy.textmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senaaksoy.textmaster.components.myButton
import com.senaaksoy.textmaster.components.MyControl
import com.senaaksoy.textmaster.viewmodel.TextMasterViewModel


@Composable
fun MyScreen(viewModel: TextMasterViewModel = viewModel()) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val isFirstLaunch = rememberSaveable { mutableStateOf(true) }


    LaunchedEffect(viewModel.inputText) {
        if (isFirstLaunch.value) {

            isFirstLaunch.value = false
        } else {
            if (viewModel.inputText.isEmpty()) {
                viewModel.resultText.value = ""
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF000000),
                                Color(0xFF0E4560)
                            )
                        )
                    )
                    .padding(16.dp),
                color = Color.Transparent
            ) {
                Text(
                    text = stringResource(id = R.string.TextMaster),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.background(color = Color(0xFF04131A))){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

            ) {

                TextField(
                    value = viewModel.inputText,
                    onValueChange = { viewModel.upDateInputText(it) },
                    label = { Text(text = stringResource(id = R.string.enter_text)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 32.dp, start = 32.dp, end = 32.dp)
                        .shadow(elevation = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = false
                )
                Row(modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    myButton(text = R.string.show_options, onClickAction = {viewModel.optionsButtonCheck()
                        keyboardController?.hide()})
                }
                MyControl()
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    viewModel.ShowResultTextCheck()
                }
            }
        }
    }
}

