/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context.unit

import com.nrkei.project.context.Context
import com.nrkei.project.context.toContext
import com.nrkei.project.context.toJson
import com.nrkei.project.context.util.TestLabels.ADDRESS
import com.nrkei.project.context.util.TestLabels.AGE
import com.nrkei.project.context.util.TestLabels.BIRTHDATE
import com.nrkei.project.context.util.TestLabels.HOUSE_NUMBER
import com.nrkei.project.context.util.TestLabels.NAME
import com.nrkei.project.context.util.TestLabels.STREET
import com.nrkei.project.context.util.TestLabels.WEIGHT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

// Ensures a Context can be rendered and restored from JSON
internal class JsonTest {

    @Test
    fun `to and from StoredEntries`() {
        Context().also { original ->
            original[AGE] = 18
            original[NAME] = "John Doe"
            original[WEIGHT] = 70.5
            original[BIRTHDATE] = LocalDate.of(1958, 3, 5)

            original.toJson().also { jsonString ->
                jsonString.toContext().also { restored ->
                    restored[NAME] = restored[NAME] + ", Jr"
                    assertEquals("John Doe, Jr", restored[NAME])

                    restored[AGE] = restored[AGE] + 2
                    assertEquals(20, restored[AGE])

                    restored[WEIGHT] = restored[WEIGHT] - 2
                    assertEquals(68.5, restored[WEIGHT])

                    restored[BIRTHDATE] = restored[BIRTHDATE].plusDays(2)
                    assertEquals(LocalDate.of(1958, 3, 7), restored[BIRTHDATE])
                }
            }
        }
    }

    @Test
    fun `context of context persistence`() {
        Context().also { original ->
            original[NAME] = "John Doe"
            original[ADDRESS] = Context().apply {
                this[STREET] = "Main Street"
                this[HOUSE_NUMBER] = 123
            }
            original.toJson().also { jsonString ->
                println(jsonString)
                jsonString.toContext().also { restored ->
                    assertEquals("John Doe", restored[NAME])
                    assertEquals("Main Street", restored[ADDRESS][STREET])
                    assertEquals(123, restored[ADDRESS][HOUSE_NUMBER])
                }
            }
        }
    }
}