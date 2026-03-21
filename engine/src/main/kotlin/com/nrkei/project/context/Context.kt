/*
 * Copyright (c) 2026 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.context

import kotlin.collections.toMutableMap

// Understands information useful for Tasks
class Context(values: Map<ContextLabel<*>, Any> = emptyMap()) {
    private val values = values.toMutableMap()

    operator fun <T> set(label: ContextLabel<T>, value: T) {
        values[label] = value as Any
    }

    operator fun <T> get(label: ContextLabel<T>): T {
        @Suppress("UNCHECKED_CAST")
        return (values[label] as? T) ?: throw IllegalStateException("No value exists for ${label.name}")
    }
}

abstract class ContextLabel<T>(val name: String) {
    override fun equals(other: Any?) = this === other ||
            other is ContextLabel<T> && this.name == other.name

    override fun hashCode() = name.hashCode()
}

