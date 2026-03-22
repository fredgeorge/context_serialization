/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nrkei.project.context.JsonSupport.mapper

// Understands SOMETHING_DUMMY
object JsonSupport {
    val mapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
}

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

inline fun <reified T> ObjectMapper.readValueTyped(json: String): T =
    readValue(json, object : TypeReference<T>() {})