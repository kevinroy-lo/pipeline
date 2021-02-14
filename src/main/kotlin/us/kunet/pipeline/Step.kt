package us.kunet.pipeline

import kotlin.reflect.KClass

typealias RegularStep<T> = Step<T, RegularExecution<T>>

/**e
 * Represents a step in a [Pipeline].
 */
abstract class Step<T : Any, E : Execution<T>>(val depends: List<KClass<out Step<T, E>>>) {
    /**
     * Runs the given step inside the pipeline execution flow.
     * @param execution the current execution context.
     * @throws PipelineLeakException if there is a leak (an exception is thrown) in the pipeline.
     */
    @Throws(PipelineLeakException::class)
    abstract suspend fun T.step(execution: E)

    /**
     * Checks whether the given step should run.
     * @param execution the current execution context.
     */
    open suspend fun T.shouldRun(execution: E) = true
}
