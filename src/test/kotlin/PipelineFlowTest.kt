import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import us.kunet.pipeline.RefinedStep
import us.kunet.pipeline.Step
import us.kunet.pipeline.impl.SequentialExecution
import us.kunet.pipeline.syncPipelineOf

class PipelineFlowTest {
    @Test
    fun `Pipeline flow should run sequentially`() {
        val pipeline = syncPipelineOf(
            AddStep(),
            DivideStep()
        )

        val target = MathTarget(5, 15, 2)
        pipeline.channelSync(target)

        Assertions.assertEquals(10, target.answer)
    }

    @Test
    fun `Pipeline flow should run with dependency checking`() {
        val pipeline = syncPipelineOf(DivideStep())

        val target = MathTarget(5, 15, 2)
        pipeline.channelSync(target)

        Assertions.assertEquals(5, target.answer)
    }

    @Test
    fun `Pipeline flow should run refined steps`() {
        val pipeline = syncPipelineOf(
            AddStep(),
            DivideStep(),
            ModuloStep()
        )

        val target = ModuloTarget(10, 3)
        pipeline.channelSync(target)

        Assertions.assertEquals(1, target.answer)
    }
}

open class MathTarget(var answer: Int = 0, val addBy: Int, val divideBy: Int)
class ModuloTarget(answer: Int, val moduloBy: Int) : MathTarget(answer, 0, 1)

class AddStep : Step<MathTarget, SequentialExecution<MathTarget>>() {
    override suspend fun MathTarget.step(execution: SequentialExecution<MathTarget>) {
        answer += addBy
    }
}

class DivideStep : Step<MathTarget, SequentialExecution<MathTarget>>(AddStep::class) {
    override suspend fun MathTarget.step(execution: SequentialExecution<MathTarget>) {
        answer /= divideBy
    }
}

class ModuloStep : RefinedStep<MathTarget, ModuloTarget, SequentialExecution<MathTarget>>(ModuloTarget::class) {
    override suspend fun ModuloTarget.refinedStep(execution: SequentialExecution<MathTarget>) {
        answer %= moduloBy
    }
}
