package us.kunet.pipeline

/**
 * This is the exception thrown when a pipeline is "leaked" (when an exception happens in a pipeline).
 * @param message the message of the pipeline leak.
 */
class PipelineLeakException(message: String) : Exception(message)
