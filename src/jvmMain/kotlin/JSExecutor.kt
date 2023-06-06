import com.google.gson.Gson
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

actual class JSExecutor {
    actual companion object {
        private val graalEngine: ScriptEngine = ScriptEngineManager().getEngineByName("graal.js")

        actual fun execute(jsCode: String): String {
            return graalEngine.eval(jsCode).toString()
        }

        actual fun execute(func: JSFunction, vararg args: Any): String {
            val argumentNames = if (func.args.joinToString { "'$it'" }.isNotEmpty())
                "${func.args.joinToString { "'$it'" }}, "
            else
                ""

            val arguments = "[${args.asList().joinToString { Gson().toJson(it) }}]"

            val jsCode = func.jsCode.replace("\"", "\\\"")
            val formattedJsCode = "return ($jsCode)"

            val executableJsCode = "((new Function($argumentNames\"$formattedJsCode\"))().apply(this, $arguments));"


            return graalEngine.eval(executableJsCode).toString()
        }
    }
}
