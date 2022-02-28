package com.example.dictionaryapp.util

import java.io.IOException

sealed class CustomExceptions: IOException(){

    class NoInternetConnectionException : CustomExceptions()

    class UnknownException : CustomExceptions()

    class ApiNotResponding : CustomExceptions()
}
