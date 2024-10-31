package com.senaaksoy.textmaster

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class TextMasterViewModel:ViewModel() {

    private val generativeModel=GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = BuildConfig.apiKey
    )

    var resultText = mutableStateOf("")

    fun performGrammarCheck(inputText: String) {
        viewModelScope.launch {
            try {
                val prompt="lütfen aşağıdaki metinde dil bilgisi hatalarını düzelt : \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                // response.text nullable olduğu için varsayılan bir değer atanıyor
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            }
        }
    }

    fun convertToFormalStyle(inputText: String) {
        viewModelScope.launch {
            try {
                val prompt = "Lütfen aşağıdaki metni resmi bir tarzda yeniden yaz: \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            }
        }
    }
    fun convertToInformalStyle(inputText: String) {
        viewModelScope.launch {
            try {
                val prompt = "Lütfen aşağıdaki metni daha samimi ve gündelik bir dilde argo kullanmadan yazın:  \"$inputText\""
                val response = generativeModel.generateContent(prompt)
                resultText.value = response.text ?: "No response text available"
            } catch (e: Exception) {
                resultText.value = "Error: ${e.message}"
            }
        }
    }




















}