package us.kunet.pipeline.impl

import us.kunet.pipeline.Execution
import us.kunet.pipeline.Pipeline
import us.kunet.pipeline.Step
import kotlin.reflect.KClass

class SequentialExecution<T : Any>(
    val target: T,
    private val continueWithLeaks: Boolean,
    private val steps: MutableList<Step<T, SequentialExecution<T>>>,
    val pipeline: Pipeline<T>,
    val runSteps: MutableList<Step<T, SequentialExecution<T>>> = mutableListOf(),
    var done: Boolean = false
) : Execution<T> {
    override suspend fun run() {
        if (done) return
        for (step in steps) {
            if (done) return
            if (!step.run { target.shouldRun(this@SequentialExecution) }) continue
            if (!step.depends.all { runSteps.hasStep(it) }) continue
            try {
                step.run { target.step(this@SequentialExecution) }
                runSteps.add(step)
            } catch (e: Exception) {
                System.err.println("Leak detected in pipeline, step ${step::class.simpleName}.")
                e.printStackTrace()
                if (!continueWithLeaks) break
            }
        }
        done = true
    }

    private fun Collection<Step<T, SequentialExecution<T>>>.hasStep(stepType: KClass<out Step<T, SequentialExecution<T>>>) =
        any(stepType::isInstance)
}
