package com.senaaksoy.textmaster.viewmodel

import android.net.Uri

data class TextUiState(
    val showOptions : Boolean =false,
    val showStyleOptions : Boolean=false,
    val showFileOptions : Boolean=false,
    var fileUri : Uri? =null,
    val isLoading: Boolean = false

)
