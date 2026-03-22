/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

// Understands data for rendering and restoring a Context
data class StoredEntry(
    val label: String,
    val type: String,
    val value: String
)

// ---------- Serialization ----------

fun serializeContext(context: Context): List<StoredEntry> =
    context.entries().map(::serializeEntry)

private fun serializeEntry(entry: ContextEntry<*>): StoredEntry =
    serializeTypedEntry(entry)

private fun <T> serializeTypedEntry(entry: ContextEntry<T>): StoredEntry =
    StoredEntry(
        label = entry.label.name,
        type = entry.label.codec.typeName,
        value = entry.label.codec.encode(entry.value)
    )

// ---------- Deserialization ----------

fun deserializeContext(storedEntries: List<StoredEntry>) = Context()
    .also { context -> storedEntries.forEach { restoreEntry(context, it) }
}

private fun restoreEntry(context: Context, stored: StoredEntry) {
    ContextLabelRegistry.find(stored.label).also { label ->
        require(label.codec.typeName == stored.type) {
            "Type mismatch for label '${stored.label}': stored type '${stored.type}', expected '${label.codec.typeName}'"
        }
        @Suppress("UNCHECKED_CAST")
        restoreTyped(context, label as ContextLabel<Any>, stored.value)
    }
}

private fun <T: Any> restoreTyped(
    context: Context,
    label: ContextLabel<T>,
    rawValue: String
) {
    context[label] = label.codec.decode(rawValue)
}