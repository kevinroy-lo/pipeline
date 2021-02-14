package us.kunet.pipeline

/**
 * This is a representation of steps (pipeline flow) run to a specific type of target
 * @param T the type of the target.
 */
interface Pipeline<T : Any> {
    /**
     * Executes the pipeline flow to a target.
     * @param target the pipeline flow target.
     * @param continueWithLeaks whether to continue the pipeline flow if there is a pipeline leak.
     */
    suspend fun channel(target: T, continueWithLeaks: Boolean = true): Execution<T>
}
