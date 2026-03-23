/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

import com.nrkei.project.context.JsonSupport.mapper

// Understands rendering a Context to and from JSON
fun Context.toJson() = mapper.writeValueAsString(serializeContext(this))

fun String.toContext(): Context {val restoredEntries: List<StoredEntry> =
    mapper.readValue(
        this,
        mapper.typeFactory.constructCollectionType(
            List::class.java,
            StoredEntry::class.java
        )
    )
    return deserializeContext(restoredEntries)
}
