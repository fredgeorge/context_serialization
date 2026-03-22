/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context

import java.time.LocalDate

// Understands serializing and deserializing of Context values
interface ValueCodec<T> {
    val typeName: String
    fun encode(value: T): String
    fun decode(text: String): T
}

object IntCodec : ValueCodec<Int> {
    override val typeName: String = "kotlin.Int"
    override fun encode(value: Int): String = value.toString()
    override fun decode(text: String): Int = text.toInt()
}

object DoubleCodec : ValueCodec<Double> {
    override val typeName: String = "kotlin.Double"
    override fun encode(value: Double): String = value.toString()
    override fun decode(text: String): Double = text.toDouble()
}

object StringCodec : ValueCodec<String> {
    override val typeName: String = "kotlin.String"
    override fun encode(value: String): String = value
    override fun decode(text: String): String = text
}

object DateCodec : ValueCodec<LocalDate> {
    override val typeName: String = "java.time.LocalDate"
    override fun encode(value: LocalDate): String = value.toString()   // ISO-8601
    override fun decode(text: String): LocalDate = LocalDate.parse(text)
}

object ContextCodec : ValueCodec<Context> {
    override val typeName = "Context"
    private val mapper = JsonSupport.mapper

    override fun encode(value: Context) =
        mapper.writeValueAsString(serializeContext(value))

    override fun decode(text: String): Context {
        val stored: List<StoredEntry> =
            mapper.readValue(text,
                mapper.typeFactory.constructCollectionType(
                    List::class.java,
                    StoredEntry::class.java
                )
            )
        return deserializeContext(stored)
    }
}

