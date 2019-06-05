/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.espressoserver.lib.handlers

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

import io.appium.espressoserver.lib.handlers.exceptions.AppiumException
import io.appium.espressoserver.lib.model.AppiumParams
import io.appium.espressoserver.lib.model.WindowSize

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import io.appium.espressoserver.lib.helpers.AndroidLogger

class GetWindowSize : RequestHandler<AppiumParams, WindowSize> {

    @Throws(AppiumException::class)
    override fun handleInternal(params: AppiumParams): WindowSize {
        AndroidLogger.logger.info("Get window size of the device")
        val displayMetrics = DisplayMetrics()
        val context = getApplicationContext<Context>()
        val winManager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                ?: throw AppiumException("Couldn't get default display: " +
                        "context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE) is null")

        winManager.defaultDisplay.getMetrics(displayMetrics)
        return WindowSize(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}