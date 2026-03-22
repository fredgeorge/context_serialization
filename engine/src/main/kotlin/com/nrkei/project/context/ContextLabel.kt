/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

// Understands the name of a specific piece of information for a Task
class ContextLabel<T>(
    val name: String,
    val codec: ValueCodec<T>
) {
    override fun toString(): String = name
}

fun <T> label(
    name: String,
    codec: ValueCodec<T>
): ContextLabel<T> =
    ContextLabel(name, codec).also(ContextLabelRegistry::register)
