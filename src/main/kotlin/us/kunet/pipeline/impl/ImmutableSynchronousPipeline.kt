package us.kunet.pipeline.impl

import kotlinx.coroutines.runBlocking
import us.kunet.pipeline.Execution
import us.kunet.pipeline.RegularExecution
import us.kunet.pipeline.Step

/**
 * Represents a synchronous pipeline.
 * @see ImmutablePipeline
 */
open class ImmutableSynchronousPipeline<T : Any>(steps: List<Step<T, RegularExecution<T>>>) : ImmutablePipeline<T>(steps) {
    /**
     * Channels the pipeline synchronously.
     */
    fun channelSync(target: T, continueWithLeaks: Boolean = true): Execution<T> =
        runBlocking { channel(target, continueWithLeaks) }
}
