package com.senaaksoy.textmaster.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senaaksoy.textmaster.R
import com.senaaksoy.textmaster.viewmodel.TextMasterViewModel

@Composable
fun MyControl(viewModel: TextMasterViewModel = viewModel()){
    val keyboardController = LocalSoftwareKeyboardController.current
    val controlUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    val rotation = if (controlUiState.isLoading) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing)
            )
        ).value
    } else {
        0f // Animasyon durduğunda döndürme açısı sıfırlanır
    }

    // ActivityResultLauncher for selecting a file
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            controlUiState.fileUri = uri // Seçilen dosyanın URI'sini alıyoruz
            controlUiState.fileUri?.let {
                viewModel.extractTextFromPdf(context, it) // PDF metnini çıkarıyoruz
            }
        }
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (controlUiState.isLoading) {
            // Animasyonlu bir progress göstergesi
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = rotation)
            ) {
                CircularProgressIndicator()
            }
        }
    if (controlUiState.showOptions) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyButton(text = R.string.grammer_check, onClickAction = { viewModel.grammerButtonCheck()
                keyboardController?.hide()
                })
            MyButton(text = R.string.change_text_style , onClickAction = {viewModel.textStyleButtonCheck()
                keyboardController?.hide()})
            MyButton(text = R.string.completeText , onClickAction = {viewModel.complateButtonCheck()
                keyboardController?.hide() })
            MyButton(text = R.string.createText, onClickAction = {viewModel.createNewTextButtonCheck()
                keyboardController?.hide()})
            MyButton(text = R.string.answerthequestion , onClickAction = { viewModel.questionAndAnswerButtonCheck()
                keyboardController?.hide()})
            MyButton(text = R.string.uploadFile , onClickAction = { activityResultLauncher.launch("application/pdf")
                viewModel.uploadButtonCheck()
                keyboardController?.hide() })
            MyButton(text = R.string.translate, onClickAction = {viewModel.translateButtonCheck()
            keyboardController?.hide()})
                MyButton(
                    text = R.string.generate_code,
                     modifier = Modifier
                         .padding(end = 4.dp)
                         .fillMaxWidth() ,
                    onClickAction = { viewModel.generateCodeButtonCheck()
                    keyboardController?.hide()
                })
        }
    }
    if (controlUiState.showFileOptions) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MyButton(text = R.string.summarize_the_text, onClickAction = {viewModel.summarizeText()
                keyboardController?.hide()})
            MyButton(text = R.string.answer_the_question, onClickAction = { viewModel.generateQuestionAnswer()
                keyboardController?.hide()})
        }
    }

    if(controlUiState.showStyleOptions){
        MyButton(text = R.string.toinformal , onClickAction = { viewModel.convertToInformalStyle(viewModel.inputText)
            keyboardController?.hide() })
        MyButton(text = R.string.toformal, onClickAction = { viewModel.convertToFormalStyle(viewModel.inputText)
            keyboardController?.hide()})
    }

}}