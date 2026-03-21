/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context

import java.time.LocalDate
import kotlin.collections.toMutableMap

// Identifies a piece of information useful for Tasks
interface ValueCodec<T> {
    val typeName: String
    fun encode(value: T): String
    fun decode(text: String): T
}

object IntCodec : ValueCodec<Int> {
    override val typeName = "Int"
    override fun encode(value: Int): String = value.toString()
    override fun decode(text: String): Int = text.toInt()
}

object DoubleCodec : ValueCodec<Double> {
    override val typeName = "Double"
    override fun encode(value: Double): String = value.toString()
    override fun decode(text: String): Double = text.toDouble()
}

object StringCodec : ValueCodec<String> {
    override val typeName = "String"
    override fun encode(value: String): String = value
    override fun decode(text: String): String = text
}

object LocalDateCodec : ValueCodec<LocalDate> {
    override val typeName = "LocalDate"
    override fun encode(value: LocalDate): String =
        value.toString()   // ISO-8601: "2026-03-21"
    override fun decode(text: String): LocalDate =
        LocalDate.parse(text)
}

class ContextLabel<T> private constructor(
    val name: String,
    val codec: ValueCodec<T>
) {
    companion object {
        fun Int(name: String) = ContextLabel(name, IntCodec)
        fun String(name: String) = ContextLabel(name, StringCodec)
        fun Double(name: String) = ContextLabel(name, DoubleCodec)
        fun Date(name: String) = ContextLabel(name, LocalDateCodec)
    }
}

// Understands information useful for Tasks
class Context(
    values: Map<ContextLabel<*>, Any> = mutableMapOf()
) {
    private val values = values.toMutableMap()

    operator fun <T: Any> set(label: ContextLabel<T>, value: T) {
        values[label] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T: Any> get(label: ContextLabel<T>) =
        values[label] as T

    operator fun contains(label: ContextLabel<*>) =
        label in values
}

