/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context.unit

import com.nrkei.project.context.Context
import com.nrkei.project.context.util.TestLabels.AGE
import com.nrkei.project.context.util.TestLabels.BIRTHDATE
import com.nrkei.project.context.util.TestLabels.NAME
import com.nrkei.project.context.util.TestLabels.WEIGHT
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

            // These will NOT compile:
//             context[AGE] = "hello"
//             context[NAME] = 18
//             context[WEIGHT] = LocalDate.of(1958, 3, 5)
//             context[BIRTHDATE] = 70.5
        }
    }
}
