package com.rappi.detail.impl.common

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

open class BaseRobot constructor(private val testRule: ComposeTestRule) {

    fun getString(@StringRes res: Int) = testRule.activity.getString(res)
}

typealias ComposeTestRule = AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>