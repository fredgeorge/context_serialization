/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

// Understands all the labels that can be used in a Context
object ContextLabelRegistry {
    private val byName = linkedMapOf<String, ContextLabel<*>>()

    fun register(label: ContextLabel<*>) {
        val existing = byName.putIfAbsent(label.name, label)
        require(existing == null) {
            "Duplicate ContextLabel name '${label.name}'"
        }
    }

    fun find(name: String): ContextLabel<*> =
        byName[name] ?: error("Unknown ContextLabel '$name'")

    fun all(): List<ContextLabel<*>> = byName.values.toList()
}