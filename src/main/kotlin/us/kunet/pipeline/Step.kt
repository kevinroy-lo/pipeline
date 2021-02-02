package us.kunet.pipeline

import kotlin.reflect.KClass

abstract class Step<T : Any, E : Execution<T>>(vararg val depends: KClass<out Step<T, E>>) {
    /**
     * Runs the given step inside the pipeline execution flow
     * @throws PipelineLeakException catch leaks in the pipeline
     */
    @Throws(PipelineLeakException::class)
    abstract suspend fun T.step(execution: E)

    /**
     * Overrideable method to check whether the current step should run
     * @param execution The current execution context
     */
    open suspend fun T.shouldRun(execution: E): Boolean = true
}
