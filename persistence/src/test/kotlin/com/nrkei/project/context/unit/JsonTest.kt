/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context.unit

import com.nrkei.project.context.Context
import com.nrkei.project.context.toContext
import com.nrkei.project.context.toJson
import com.nrkei.project.context.util.Country.NORWAY
import com.nrkei.project.context.util.TestLabels.ADDRESS
import com.nrkei.project.context.util.TestLabels.AGE
import com.nrkei.project.context.util.TestLabels.BIRTHDATE
import com.nrkei.project.context.util.TestLabels.BORROWERS
import com.nrkei.project.context.util.TestLabels.CITIZENSHIP
import com.nrkei.project.context.util.TestLabels.HOUSE_NUMBER
import com.nrkei.project.context.util.TestLabels.NAME
import com.nrkei.project.context.util.TestLabels.PRODUCT
import com.nrkei.project.context.util.TestLabels.STREET
import com.nrkei.project.context.util.TestLabels.WEIGHT
import org.junit.jupiter.api.Assertions
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
                    Assertions.assertEquals("John Doe, Jr", restored[NAME])

                    restored[AGE] = restored[AGE] + 2
                    Assertions.assertEquals(20, restored[AGE])

                    restored[WEIGHT] = restored[WEIGHT] - 2
                    Assertions.assertEquals(68.5, restored[WEIGHT])

                    restored[BIRTHDATE] = restored[BIRTHDATE].plusDays(2)
                    Assertions.assertEquals(LocalDate.of(1958, 3, 7), restored[BIRTHDATE])
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
                this[CITIZENSHIP] = NORWAY
            }
            original.toJson().also { jsonString ->
                println(jsonString)
                jsonString.toContext().also { restored ->
                    Assertions.assertEquals("John Doe", restored[NAME])
                    Assertions.assertEquals("Main Street", restored[ADDRESS][STREET])
                    Assertions.assertEquals(123, restored[ADDRESS][HOUSE_NUMBER])
                    Assertions.assertEquals(NORWAY, restored[ADDRESS][CITIZENSHIP])
                }
            }
        }
    }

    @Test
    fun `context of context persistence with sub-context for each person`() {
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
            context.toJson().also { jsonString ->
                jsonString.toContext().also { restored ->
                    Assertions.assertEquals("Credit Card", restored[PRODUCT])
                    Assertions.assertEquals("Main Street", restored[ADDRESS][STREET])
                    Assertions.assertEquals(123, restored[ADDRESS][HOUSE_NUMBER])
                    Assertions.assertEquals("John Doe", restored[BORROWERS][0][NAME])
                    Assertions.assertEquals(42, restored[BORROWERS][0][AGE])
                    Assertions.assertEquals("Jane Doe", restored[BORROWERS][1][NAME])
                    Assertions.assertEquals(45, restored[BORROWERS][1][AGE])
                }
            }
        }
    }
}