package us.kunet.pipeline

/**
 * This represents a specific instance of a pipeline being run.
 * @param T the type of the target.
 */
interface Execution<T : Any> {
    /**
     * Run the execution.
     */
    suspend fun run()
}
