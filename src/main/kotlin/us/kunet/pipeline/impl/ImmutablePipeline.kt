package us.kunet.pipeline.impl

import us.kunet.pipeline.*

/**
 * Represents a pipeline which cannot be mutated.
 * @see Pipeline
 */
open class ImmutablePipeline<T : Any>(override val steps: List<Step<T, RegularExecution<T>>>) : SteppingPipeline<T, RegularExecution<T>> {
    override suspend fun channel(target: T, continueWithLeaks: Boolean): Execution<T> {
        return SequentialExecution(target, continueWithLeaks, steps, this)
            .also { it.run() }
    }
}
