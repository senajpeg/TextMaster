package com.senaaksoy.textmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MyScreen(viewModel: TextMasterViewModel = viewModel<TextMasterViewModel>()) {
    var inputText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }
    var showStyleOptions by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF9EA6AD))
                    .padding(16.dp),
                color = Color.Transparent
            ) {
                Text(
                    text = stringResource(id = R.string.TextMaster),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),

        ) {

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text(text = stringResource(id = R.string.enter_text)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = false
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    showOptions = if (showOptions) {
                        showStyleOptions = false
                        false
                    } else {
                        showStyleOptions = false
                        true
                    }        },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor =Color(0xFF9EA6AD) )
                ) {
                    Text(text = stringResource(id = R.string.show_options))
                }
            }
            if (showOptions) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.performGrammarCheck(inputText)},
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor =Color(0xFF9EA6AD) )) {
                        Text(text = stringResource(id = R.string.grammer_check))
                    }
                    Button(onClick = {
                                     showOptions=false
                                     showStyleOptions=true

                    },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor =Color(0xFF9EA6AD) )) {
                        Text(text = stringResource(id = R.string.change_text_style))
                    }
                }
            }
            if(showStyleOptions){
                Button(onClick = { viewModel.convertToInformalStyle(inputText) },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor =Color(0xFF9EA6AD) )
                    ) {
                    Text(text = stringResource(id = R.string.toinformal))
                }
                Button(onClick = { viewModel.convertToFormalStyle(inputText) },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor =Color(0xFF9EA6AD) )
                ) {
                    Text(text = stringResource(id = R.string.toformal))
                }

            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(200.dp),
                shadowElevation = 4.dp
            ) {
                Box(
                    modifier= Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ){
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // resultText boş değilse başlığı göster
                        if (viewModel.resultText.value.isEmpty()) {
                            Text(text = stringResource(id = R.string.result))
                        }
                        else
                            Text(text = viewModel.resultText.value)
                    }
                }

            }
        }
    }
}

