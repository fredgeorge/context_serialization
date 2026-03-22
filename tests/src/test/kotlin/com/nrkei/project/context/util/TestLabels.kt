/*
 *  Copyright (c) 2000-2026 by Fred George
 *  May be used freely except for training; license required for training.
 *  @author Fred George  fredgeorge@acm.org
 */

package com.nrkei.project.context.util

import com.nrkei.project.context.DateCodec
import com.nrkei.project.context.DoubleCodec
import com.nrkei.project.context.IntCodec
import com.nrkei.project.context.StringCodec
import com.nrkei.project.context.label

// Understands SOMETHING_DUMMY
internal object TestLabels {
   internal val NAME = label("NAME", StringCodec)
   internal val AGE = label("AGE", IntCodec)
   internal val WEIGHT = label("WEIGHT", DoubleCodec)
   internal val BIRTHDATE= label("BIRTHDATE", DateCodec)
}