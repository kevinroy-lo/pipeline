package us.kunet.pipeline

import kotlin.reflect.KClass

/**
 * Refined Step is a step that will only be stepped if the given target is of a specific type
 */
@Suppress("UNCHECKED_CAST")
abstract class RefinedStep<T : Any, R : T, E : Execution<T>>(
    private val refined: KClass<R>,
    vararg depends: KClass<out Step<T, E>>
) : Step<T, E>(*depends) {
    final override suspend fun T.step(execution: E) {
        if (refined.isInstance(this)) (this as R).refinedStep(execution)
    }

    /**
     * Runs the given step inside the pipeline execution flow
     * @throws PipelineLeakException catch leaks in the pipeline
     */
    @Throws(PipelineLeakException::class)
    abstract suspend fun R.refinedStep(execution: E)

    final override suspend fun T.shouldRun(execution: E): Boolean {
        return refined.isInstance(this) && (this as R).refinedShouldRun(execution)
    }

    /**
     * Overrideable method to check whether the current step should run
     * @param execution The current execution context
     */
    open suspend fun R.refinedShouldRun(execution: E): Boolean = true
}
