package us.kunet.pipeline

import us.kunet.pipeline.impl.ImmutablePipeline
import us.kunet.pipeline.impl.ImmutableSynchronousPipeline
import us.kunet.pipeline.impl.SequentialExecution
import kotlin.reflect.KClass

/**
 * Creates an immutable pipeline of the given steps.
 * @param steps the execution steps of the pipeline.
 * @return the new pipeline.
 */
fun <T : Any> pipelineOf(vararg steps: Step<T, RegularExecution<T>>): Pipeline<T> =
    ImmutablePipeline(steps.toList())

/**
 * Creates an immutable synchronous pipeline of the given steps.
 * @param steps The steps of the pipeline
 * @return Returns the new pipeline
 */
fun <T : Any> syncPipelineOf(vararg steps: Step<T, RegularExecution<T>>): ImmutableSynchronousPipeline<T> =
    ImmutableSynchronousPipeline(steps.toList())

inline fun <T : Any, E : Execution<T>> step(
    vararg depends: KClass<out Step<T, E>>,
    crossinline shouldRun: T.(E) -> Boolean = { true },
    crossinline function: T.(E) -> Unit
) = object : Step<T, E>(depends.toList()) {
    override suspend fun T.step(execution: E) {
        return function(execution)
    }

    override suspend fun T.shouldRun(execution: E) = shouldRun(execution)
}

inline fun <T : Any> regularStep(
    vararg depends: KClass<out Step<T, RegularExecution<T>>>,
    crossinline shouldRun: T.(RegularExecution<T>) -> Boolean = { true },
    crossinline function: T.(RegularExecution<T>) -> Unit
) = object : RegularStep<T>(depends.toList()) {
    override suspend fun T.step(execution: RegularExecution<T>) {
        return function(execution)
    }

    override suspend fun T.shouldRun(execution: RegularExecution<T>) = shouldRun(execution)
}

inline fun <T : Any, reified R : T, E : Execution<T>> refinedStep(
    vararg depends: KClass<out Step<T, E>>,
    crossinline shouldRun: R.(E) -> Boolean = { true },
    crossinline function: R.(E) -> Unit
) = object : RefinedStep<T, R, E>(R::class, depends.toList()) {
    override suspend fun R.refinedStep(execution: E) {
        return function(execution)
    }

    override suspend fun R.refinedShouldRun(execution: E) = shouldRun(execution)
}

inline fun <T : Any, reified R : T> regularRefinedStep(
    vararg depends: KClass<out Step<T, RegularExecution<T>>>,
    crossinline shouldRun: R.(RegularExecution<T>) -> Boolean = { true },
    crossinline function: R.(RegularExecution<T>) -> Unit
) = object : RegularRefinedStep<T, R>(R::class, depends.toList()) {
    override suspend fun R.refinedStep(execution: RegularExecution<T>) {
        return function(execution)
    }

    override suspend fun R.refinedShouldRun(execution: RegularExecution<T>) = shouldRun(execution)
}