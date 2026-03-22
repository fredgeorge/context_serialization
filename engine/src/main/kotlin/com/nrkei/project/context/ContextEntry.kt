/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

// Understands SOMETHING_DUMMY
data class ContextEntry<T>(
    val label: ContextLabel<T>,
    val value: T
) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun unsafe(label: ContextLabel<*>, value: Any): ContextEntry<*> =
            ContextEntry(label as ContextLabel<Any>, value)
    }
}