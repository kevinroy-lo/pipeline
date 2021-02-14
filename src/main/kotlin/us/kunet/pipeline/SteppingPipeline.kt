package us.kunet.pipeline

/**
 * Represents a pipeline with steps of execution.
 * @see Pipeline
 */
interface SteppingPipeline<T : Any, E : Execution<T>> : Pipeline<T> {
    /**
     * The steps of the pipeline execution.
     */
    val steps: List<Step<T, E>>
}