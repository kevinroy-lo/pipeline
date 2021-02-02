package us.kunet.pipeline

/**
 * The pipeline is a representation of steps (pipeline flow) run to a specific type of target
 * @param T Target type
 */
interface Pipeline<T : Any> {
    /**
     * Execute the pipeline flow to a target
     * @param target Pipeline flow target
     * @param continueWithLeaks Whether to continue the pipeline flow if there is a pipeline leak
     */
    suspend fun channel(target: T, continueWithLeaks: Boolean = true): Execution<T>
}
