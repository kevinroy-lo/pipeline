package us.kunet.pipeline

import us.kunet.pipeline.impl.ImmutablePipeline
import us.kunet.pipeline.impl.ImmutableSynchronousPipeline
import us.kunet.pipeline.impl.SequentialExecution

/**
 * Creates an immutable pipeline of the given steps
 * @param steps The steps of the pipeline
 * @return Returns the new pipeline
 */
fun <T : Any> pipelineOf(vararg steps: Step<T, SequentialExecution<T>>): Pipeline<T> =
    ImmutablePipeline(steps.toList())

/**
 * Creates an immutable synchronous pipeline of the given steps
 * @param steps The steps of the pipeline
 * @return Returns the new pipeline
 */
fun <T : Any> syncPipelineOf(vararg steps: Step<T, SequentialExecution<T>>): ImmutableSynchronousPipeline<T> =
    ImmutableSynchronousPipeline(steps.toList())
