/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context.unit

import com.nrkei.project.context.Context
import com.nrkei.project.context.ContextLabel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

// Ensures Context works correctly
internal class ContextTest {

    @Test fun `context holds various types`() {
        Context().also { context ->
            context[NAME] = "John Doe"
            assertEquals("John Doe", context[NAME])
            context[AGE] = 18
            assertEquals(18, context[AGE])
            context[WEIGHT] = 70.5
            assertEquals(70.5, context[WEIGHT])
            context[BIRTHDATE] = LocalDate.of(1958, 3, 5)
            assertEquals(LocalDate.of(1958, 3, 5), context[BIRTHDATE])
        }
    }

    object NAME : ContextLabel<String>("NAME")
    object AGE : ContextLabel<Int>("AGE")
    object WEIGHT : ContextLabel<Double>("WEIGHT")
    object BIRTHDATE: ContextLabel<LocalDate>("BIRTHDATE")

}