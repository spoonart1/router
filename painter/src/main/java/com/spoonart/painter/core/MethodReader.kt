package com.spoonart.painter.core

import java.util.concurrent.Executors

interface MethodReader {
    fun getCurrentMethodName(packageName: String, callback: (String) -> Unit)
}

internal class MethodReaderImpl : MethodReader {

    private val executor = Executors.newSingleThreadExecutor()
    private var cachedMessage = ""

    override fun getCurrentMethodName(packageName: String, callback: (String) -> Unit) {
        val thread = Thread.currentThread()
        val stackTraceElements = thread.stackTrace.filter { it.className.contains(packageName) }
        executor.submit {
            if (stackTraceElements.isNotEmpty()) {
                val currentElement = stackTraceElements[stackTraceElements.lastIndex]
                val currentMethod = currentElement.methodName
                val currentClass = currentElement.className
                val message = "$currentClass -> $currentMethod"
                if (cachedMessage != message) {
                    callback.invoke(message)
                }
                cachedMessage = message
            }
        }
    }

}
