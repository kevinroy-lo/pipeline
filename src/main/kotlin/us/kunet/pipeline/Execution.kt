package us.kunet.pipeline

/**
 * Execution represents a specific instance of a pipeline being "flowed"
 * @param T Target type
 */
interface Execution<T : Any> {
    /**
     * Run the execution
     */
    suspend fun run()
}
