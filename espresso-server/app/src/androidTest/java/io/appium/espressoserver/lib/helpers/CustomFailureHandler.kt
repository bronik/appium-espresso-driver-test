/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.espressoserver.lib.helpers

import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.base.DefaultFailureHandler
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicInteger

class CustomFailureHandler(appContext: Context) : FailureHandler {
    private val originalHandler = DefaultFailureHandler(appContext)

    override fun handle(error: Throwable?, viewMatcher: Matcher<View>?) {
        val failureCountField = DefaultFailureHandler::class.java.getDeclaredField("failureCount")
        failureCountField.isAccessible = true
        (failureCountField.get(originalHandler) as AtomicInteger).incrementAndGet()

        val handlersField = DefaultFailureHandler::class.java.getDeclaredField("handlers")
        handlersField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        for (handler in (handlersField.get(originalHandler) as java.util.ArrayList<FailureHandler>)) {
            handler.handle(error, viewMatcher)
        }
    }
}