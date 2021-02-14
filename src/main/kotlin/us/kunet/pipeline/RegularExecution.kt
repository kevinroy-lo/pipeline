package us.kunet.pipeline

/**
 * Represents an execution with some useful properties.
 */
interface RegularExecution<T : Any> : Execution<T> {
    /**
     * The object which the steps use.
     */
    val target: T

    /**
     * The pipeline running the execution.
     */
    val pipeline: Pipeline<T>

    /**
     * Whether the execution has finished running.
     */
    var isDone: Boolean
}