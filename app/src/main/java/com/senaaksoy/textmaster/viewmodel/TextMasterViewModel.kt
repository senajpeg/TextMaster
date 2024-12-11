package com.senaaksoy.textmaster.viewmodel

import com.senaaksoy.textmaster.BuildConfig


import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.senaaksoy.textmaster.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class TextMasterViewModel:ViewModel() {

    private val generativeModel=GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = BuildConfig.apiKey
    )

    private val _uiState= MutableStateFlow(TextUiState())
    val uiState : StateFlow<TextUiState> = _uiState.asStateFlow()


    var resultText = mutableStateOf("")
   private var extractedText = mutableStateOf("")


   var inputText by mutableStateOf("")
       private set

     fun upDateInputText(newInputText:String){
     inputText=newInputText
     }

    private fun setLoadingState(loading:Boolean){
        _uiState.update { currentState->
            currentState.copy(
                isLoading = loading
            )

        }
    }

    fun optionsButtonCheck(){
     if(_uiState.value.showOptions) {
         _uiState.update {currentState->
             currentState.copy(
                 showStyleOptions = false,
                 showOptions = false
             )
         }
     } else {
         _uiState.update { currentState->
             currentState.copy(
                 showStyleOptions=false,
                 showOptions=true
             )
         }
     }
    }
    fun grammerButtonCheck(){
        performGrammarCheck(inputText)
        _uiState.update { currentState->
            currentState.copy(
                showOptions=false
            )
        }
    }

    fun translateButtonCheck(){
        translateThetext(inputText)
        _uiState.update { currentState->
            currentState.copy(
                showOptions = false
            )
        }
    }
    fun textStyleButtonCheck(){
        _uiState.update { currentState->
            currentState.copy(
                showOptions=false,
                showStyleOptions = true
            )
        }
    }
    fun complateButtonCheck(){
        completeThetext(inputText)
        _uiState.update { currentState->
            currentState.copy(
                showOptions=false
            )
        }
    }
    fun createNewTextButtonCheck(){
        createNewText(inputText)
        _uiState.update { currentState->
            currentState.copy(
                showOptions=false
            )
        }
    }
    fun questionAndAnswerButtonCheck(){
        questionandAnswer(inputText)
        _uiState.update { currentState->
            currentState.copy(
                showOptions=false
            )
        }
    }
    fun uploadButtonCheck(){
        _uiState.update { currentState->
            currentState.copy(
                        showOptions = false,
                        showStyleOptions = false,
                        showFileOptions = true
            )
        }
    }
    @Composable
    fun ShowResultTextCheck(){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (resultText.value.isEmpty()) {
                Text(text = stringResource(id = R.string.result))
            }
            else
                Text(text = resultText.value)
        }
    }
fun generateCodeButtonCheck(){
    generateCode(inputText)
    _uiState.update { currentState->
        currentState.copy(
            showOptions = false
        )
    }
}
    private fun generateCode(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki metni bir kod parçası olarak yaz: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }
    // Extract text from PDF
    fun extractTextFromPdf(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val extracted = extractTextFromPdfMethod(context, uri)
                extractedText.value = extracted
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            }
        }
    }

    private fun extractTextFromPdfMethod(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val stringBuilder = StringBuilder()
        inputStream.use {
            val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(PdfReader(it))
            val numberOfPages = pdfDocument.numberOfPages
            for (page in 1..numberOfPages) {
                val pageText = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(page))
                stringBuilder.append(pageText).append("\n")
            }
            pdfDocument.close()
        }
        return stringBuilder.toString()
    }
    private fun translateThetext(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "lütfen aşağıdaki metni kullanıcının belirttiği dile çevir: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }
    fun summarizeText() {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki metni kısaca özetle: \"${extractedText.value}\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }

    fun generateQuestionAnswer() {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen sorduğum soruyu metnin içindeki cümlelere göre cevapla,sadece o soruyu ilgilendiren kısmı yaz: \"${extractedText.value}\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }

    private fun performGrammarCheck(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "lütfen aşağıdaki metinde dil bilgisi hatalarını düzelt: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }


    fun convertToFormalStyle(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki metni resmi bir tarzda yeniden yaz: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }
    fun convertToInformalStyle(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki metni daha samimi bir dilde yaz: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }
    private fun completeThetext(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen girilen metni mantıklı bir şekilde tamamla: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }

    private fun createNewText(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki başlığa uygun yaratıcı bir metin yaz: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }

    private fun questionandAnswer(inputText: String) {
        viewModelScope.launch {
            try {
                setLoadingState(true) // Animasyonu başlat
                val prompt = "Lütfen aşağıdaki soruya doğru bir cevap ver: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            } finally {
                setLoadingState(false) // Animasyonu durdur
            }
        }
    }

}



















