package org.theponies.ponecrafter.util

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object UuidUtil {
    /**
     * Generate a UUID that is suitable for PoneCrafter content.
     * This means the pack identifier section is always in the range 256 to 65535.
     * (0-255 are reserved for official content, 65536 and onwards are reserved for "mods")
     */
    fun generateContentUuid(): UUID {
        //Starting at 00000100, which is 16^10.
        val min = 1099511627776L
        //Up until 00010000, which is 16^12.
        val max = 281474976710656L
        val mostSignificant = ThreadLocalRandom.current().nextLong(min, max)
        val leastSignificant = ThreadLocalRandom.current().nextLong(0, java.lang.Long.MAX_VALUE)
        return UUID(mostSignificant, leastSignificant)
    }
}