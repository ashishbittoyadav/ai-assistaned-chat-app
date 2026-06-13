package com.ashish.ollama_chat_application

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class MyTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun test_screen(){
        rule.setContent { MainActivity() }
    }

}