import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

fun main() {
    val engine = ScriptEngineManager().getEngineByExtension("kts")!!
    // listOf with several elements returns java.util.Arrays$ArrayList, a private nested class of java.util.Arrays
    val bound = listOf(1, 2)
    println("Runtime class of the binding: ${bound::class.java.name}")
    // The snippet does not reference the binding: works with 2.4.10, fails to compile with 2.4.21
    println(engine.eval("1 + 1", SimpleBindings(mutableMapOf<String, Any?>("bound" to bound))))
}
