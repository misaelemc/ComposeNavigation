package com.rappi.detail.impl.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseAndroidComposeTest<T> {

    abstract val config: Config<T>

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val activity: ComponentActivity
        get() = composeTestRule.activity

    @Before
    open fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        config.init()
    }

    @After
    open fun tearDown() {}

    interface Config<T> {
        val screenState: StateFlow<T>

        fun init()
        fun setState(state: T)
    }
}