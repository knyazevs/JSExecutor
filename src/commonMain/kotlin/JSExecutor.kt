expect class JSExecutor {
    companion object {
        fun execute(jsCode: String): String
        fun execute(func: JSFunction, vararg args: Any): String
    }
}
