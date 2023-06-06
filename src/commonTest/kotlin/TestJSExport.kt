import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class TestJSExport {
    @Test
    @JsName("testSum")
    fun `test sum`() {
        assertEquals("4", JSExecutor.execute("2+2"))
    }

    @Test
    @JsName("testSimpleCalc")
    fun `test simple calc`() {
        val function = JSFunction("function test(a) { return a }")
        assertEquals("@", JSExecutor.execute(function, "@"))
    }

    @Test
    @JsName("testCalc")
    fun `test calc`() {
        val function = JSFunction("function test(a, b, c) { return a + b + c }", "a", "b", "c")
        assertEquals("12", JSExecutor.execute(function, 2, 4, 6))
    }
}
