package com.senaaksoy.textmaster.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senaaksoy.textmaster.R
import com.senaaksoy.textmaster.viewmodel.TextMasterViewModel

@Composable
fun MyControl(viewModel: TextMasterViewModel = viewModel()){
    val keyboardController = LocalSoftwareKeyboardController.current
    val controlUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current


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
    if (controlUiState.showOptions) {
        Column(modifier = Modifier.fillMaxSize()) {
            myButton(text = R.string.grammer_check, onClickAction = {viewModel.grammerButtonCheck()
                keyboardController?.hide()})
            myButton(text = R.string.change_text_style , onClickAction = {viewModel.textStyleButtonCheck()
                keyboardController?.hide()})
            myButton(text = R.string.completeText , onClickAction = {viewModel.complateButtonCheck()
                keyboardController?.hide() })
            myButton(text = R.string.createText, onClickAction = {viewModel.createNewTextButtonCheck()
                keyboardController?.hide()})
            myButton(text = R.string.answerthequestion , onClickAction = { viewModel.questionAndAnswerButtonCheck()
                keyboardController?.hide()})
            myButton(text = R.string.uploadFile , onClickAction = { activityResultLauncher.launch("application/pdf")
                viewModel.uploadButtonCheck()
                keyboardController?.hide() })
            myButton(text = R.string.translate, onClickAction = {viewModel.translateButtonCheck()
            keyboardController?.hide()})

                myButton(
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
            myButton(text = R.string.summarize_the_text, onClickAction = {viewModel.summarizeText()
                keyboardController?.hide()})
            myButton(text = R.string.answer_the_question, onClickAction = { viewModel.generateQuestionAnswer()
                keyboardController?.hide()})
        }
    }

    if(controlUiState.showStyleOptions){
        myButton(text = R.string.toinformal , onClickAction = { viewModel.convertToInformalStyle(viewModel.inputText)
            keyboardController?.hide() })
        myButton(text = R.string.toformal, onClickAction = { viewModel.convertToFormalStyle(viewModel.inputText)
            keyboardController?.hide()})
    }

}