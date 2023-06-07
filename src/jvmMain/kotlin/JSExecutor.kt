import com.google.gson.Gson
import org.mozilla.javascript.Context
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

enum class ScripEngine{
    Nashorn, Graal, Rhino
}
actual class JSExecutor {
    actual companion object {
        private val graalEngine: ScriptEngine = ScriptEngineManager().getEngineByName("graal.js")
        private val nashornEngine: ScriptEngine = ScriptEngineManager().getEngineByName("Nashorn")

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

            val engine = ScripEngine.Nashorn
            return when (engine) {
                ScripEngine.Rhino -> try {
                    val rhino: Context = Context.enter()
                    val scope = rhino.initStandardObjects()
                    return rhino.evaluateString(scope, executableJsCode, "JavaScript", 1, null).toString()
                } finally {
                    Context.exit()
                }

                ScripEngine.Graal -> graalEngine.eval(executableJsCode).toString()
                ScripEngine.Nashorn -> nashornEngine.eval(executableJsCode).toString()
                else -> {
                    throw NotImplementedError()}
            }
        }
    }
}
