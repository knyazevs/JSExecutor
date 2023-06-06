actual class JSExecutor {
    actual companion object {
        actual fun execute(jsCode: String): String {
            return eval(jsCode).toString()
        }

        actual fun execute(func: JSFunction, vararg args: Any): String {
            val formattedJsCode = "return (${func.jsCode})"
            val argumentNames = func.args.joinToString()

            return js("(new Function(argumentNames, formattedJsCode))().apply(this, args)").toString()
        }
    }
}
