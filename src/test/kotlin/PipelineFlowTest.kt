import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import us.kunet.pipeline.*
import us.kunet.pipeline.impl.SequentialExecution

class PipelineFlowTest {
    @Test
    fun `Pipeline flow should run sequentially`() {
        val pipeline = syncPipelineOf(
            addStep(),
            divideStep()
        )

        val target = MathTarget(5, 15, 2)
        pipeline.channelSync(target)

        Assertions.assertEquals(10, target.answer)
    }

    @Test
    fun `Pipeline flow should run with dependency checking`() {
        val pipeline = syncPipelineOf(divideStep())

        val target = MathTarget(5, 15, 2)
        pipeline.channelSync(target)

        Assertions.assertEquals(5, target.answer)
    }

    @Test
    fun `Pipeline flow should run refined steps`() {
        val pipeline = syncPipelineOf(
            addStep(),
            divideStep(),
            moduloStep()
        )

        val target = ModuloTarget(10, 3)
        pipeline.channelSync(target)

        Assertions.assertEquals(1, target.answer)
    }
}

open class MathTarget(var answer: Int = 0, val addBy: Int, val divideBy: Int)
class ModuloTarget(answer: Int, val moduloBy: Int) : MathTarget(answer, 0, 1)

fun addStep() = regularStep<MathTarget> {
    answer += addBy
}

fun divideStep() = regularStep<MathTarget> {
    answer /= divideBy
}

fun moduloStep() = regularRefinedStep<MathTarget, ModuloTarget> {
    answer %= moduloBy
}