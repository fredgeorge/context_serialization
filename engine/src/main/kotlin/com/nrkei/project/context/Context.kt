/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context

// Understands information useful for Tasks
class Context(
    values: Map<ContextLabel<*>, Any> = emptyMap(),
) {
    private val values = values.toMutableMap()

    operator fun <T: Any> set(label: ContextLabel<T>, value: T) {
        values[label] = value as Any
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T: Any> get(label: ContextLabel<T>) = values[label] as? T
            ?: throw NoSuchElementException("No value stored for label '${label.name}'")

    infix operator fun contains(label: ContextLabel<*>) =
        label in values

    infix fun remove(label: ContextLabel<*>) {
        values.remove(label)
    }

    fun isEmpty() = values.isEmpty()

    fun entries() =
        values.map { (label, value) -> ContextEntry.unsafe(label, value) }

    override fun toString() =
        values.entries.joinToString(prefix = "Context(", postfix = ")") { (label, value) ->
            "${label.name}=$value"
        }
}

