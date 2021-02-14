package us.kunet.pipeline.impl

import us.kunet.pipeline.Pipeline
import us.kunet.pipeline.RegularExecution
import us.kunet.pipeline.Step
import kotlin.reflect.KClass

/**
 * Represents an execution where the steps are run in order, one after the other.
 */
class SequentialExecution<T : Any>(
    override val target: T,
    private val continueWithLeaks: Boolean,
    private val steps: Iterable<Step<T, RegularExecution<T>>>,
    override val pipeline: Pipeline<T>,
) : RegularExecution<T> {
    private val runSteps = mutableListOf<Step<T, RegularExecution<T>>>()

    override var isDone = false

    override suspend fun run() {
        if (isDone) return
        for (step in steps) {
            if (isDone) return
            if (!step.run { target.shouldRun(this@SequentialExecution) }) continue
            if (!step.depends.all { runSteps.any(it::isInstance) }) continue

            try {
                step.run { target.step(this@SequentialExecution) }
                runSteps.add(step)
            } catch (e: Exception) {
                System.err.println("Leak detected in pipeline, step ${step::class.simpleName}.")
                e.printStackTrace()
                if (!continueWithLeaks) break
            }
        }
        isDone = true
    }
}