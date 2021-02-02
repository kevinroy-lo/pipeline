package us.kunet.pipeline.impl

import us.kunet.pipeline.Execution
import us.kunet.pipeline.Pipeline
import us.kunet.pipeline.Step

open class ImmutablePipeline<T : Any>(private val steps: List<Step<T, SequentialExecution<T>>>) : Pipeline<T> {
    override suspend fun channel(target: T, continueWithLeaks: Boolean): Execution<T> {
        val steps = steps.toMutableList()
        val execution = SequentialExecution(target, continueWithLeaks, steps, this)
        execution.run()
        return execution
    }
}
