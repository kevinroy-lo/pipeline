package us.kunet.pipeline

/**
 * An exception thrown when a pipeline is "leaked" (when an exception happens in a pipeline)
 * @param message The message of the pipeline leak
 */
class PipelineLeakException(message: String) : Exception(message)
