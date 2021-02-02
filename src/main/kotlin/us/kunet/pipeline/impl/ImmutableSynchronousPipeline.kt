package us.kunet.pipeline.impl

import kotlinx.coroutines.runBlocking
import us.kunet.pipeline.Execution
import us.kunet.pipeline.Step

open class ImmutableSynchronousPipeline<T : Any>(steps: List<Step<T, SequentialExecution<T>>>) : ImmutablePipeline<T>(steps) {
    fun channelSync(target: T, continueWithLeaks: Boolean = true): Execution<T> =
        runBlocking { channel(target, continueWithLeaks) }
}
