/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context.unit

import com.nrkei.project.context.Context
import com.nrkei.project.context.util.TestLabels.ADDRESS
import com.nrkei.project.context.util.TestLabels.AGE
import com.nrkei.project.context.util.TestLabels.BIRTHDATE
import com.nrkei.project.context.util.TestLabels.BORROWERS
import com.nrkei.project.context.util.TestLabels.HOUSE_NUMBER
import com.nrkei.project.context.util.TestLabels.NAME
import com.nrkei.project.context.util.TestLabels.PRODUCT
import com.nrkei.project.context.util.TestLabels.STREET
import com.nrkei.project.context.util.TestLabels.WEIGHT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

// Ensures Context works correctly
internal class ContextTest {

    @Test fun `context holds various types`() {
        Context().also { context ->
            assert(context.isEmpty())

            context[NAME] = "John Doe"
            assertEquals("John Doe", context[NAME])

            context[AGE] = 18
            assertEquals(18, context[AGE])

            context[WEIGHT] = 70.5
            assertEquals(70.5, context[WEIGHT])

            context[BIRTHDATE] = LocalDate.of(1958, 3, 5)
            assertEquals(LocalDate.of(1958, 3, 5), context[BIRTHDATE])

            context.remove(AGE)
            assertThrows<NoSuchElementException> { context[AGE] }

            assertFalse(context.isEmpty())

            // These will NOT compile:
//             context[AGE] = null
//             context[AGE] = "hello"
//             context[NAME] = 18
//             context[WEIGHT] = LocalDate.of(1958, 3, 5)
//             context[BIRTHDATE] = 70.5
        }
    }

    @Test fun `context of context`() {
        Context().also { context ->
            context[NAME] = "John Doe"
            context[ADDRESS] = Context().apply {
                this[STREET] = "Main Street"
                this[HOUSE_NUMBER] = 123
                }
            assertEquals("John Doe", context[NAME])
            assertEquals("Main Street", context[ADDRESS][STREET])
            assertEquals(123, context[ADDRESS][HOUSE_NUMBER])
        }
    }

    @Test fun `context with sub-context for each person`() {
        Context().also { context ->
            context[PRODUCT] = "Credit Card"
            context[ADDRESS] = Context().apply {
                this[STREET] = "Main Street"
                this[HOUSE_NUMBER] = 123
            }
            context[BORROWERS] = listOf<Context>(
                Context().apply {
                    this[NAME] = "John Doe"
                    this[AGE] = 42
                },
                Context().apply {
                    this[NAME] = "Jane Doe"
                    this[AGE] = 45
                },
            )
            assertEquals("Credit Card", context[PRODUCT])
            assertEquals("Main Street", context[ADDRESS][STREET])
            assertEquals(123, context[ADDRESS][HOUSE_NUMBER])
            assertEquals("John Doe", context[BORROWERS][0][NAME])
            assertEquals(42, context[BORROWERS][0][AGE])
            assertEquals("Jane Doe", context[BORROWERS][1][NAME])
            assertEquals(45, context[BORROWERS][1][AGE])
        }
    }
}
