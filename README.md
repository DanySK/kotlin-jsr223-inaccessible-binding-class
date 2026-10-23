# Kotlin JSR-223: a binding whose runtime class is inaccessible breaks every `eval()` (regression in 2.4.21)

Minimal reproducer for a regression in `kotlin-scripting-jsr223` 2.4.21.

When a binding's runtime class is not accessible from the script
(e.g., `java.util.Arrays$ArrayList`, the class returned by `listOf(1, 2)`),
the declaration that the engine synthesizes for the binding references that class,
and the snippet fails to compile, **even if the snippet never references the binding**.

```kotlin
val engine = ScriptEngineManager().getEngineByExtension("kts")!!
engine.eval("1 + 1", SimpleBindings(mutableMapOf<String, Any?>("bound" to listOf(1, 2))))
```

## Run

```sh
./gradlew run                          # Kotlin 2.4.21: fails
./gradlew run -PkotlinVersion=2.4.10   # Kotlin 2.4.10: prints 2
```

### Kotlin 2.4.21

```
Runtime class of the binding: java.util.Arrays$ArrayList
Exception in thread "main" javax.script.ScriptException: Error evaluation thhe snippet:
ERROR 'public' property exposes its 'private-in-class' type 'ArrayList'. ($$synthetic_jsr223_snippet_0.repl.kts:42:5)
ERROR Cannot access 'class ArrayList<E : Any!> : AbstractList<E!>, RandomAccess, Serializable': it is private in 'java.util.Arrays'. ($$synthetic_jsr223_snippet_0.repl.kts:42:29)
ERROR Cannot access 'class ArrayList<E : Any!> : AbstractList<E!>, RandomAccess, Serializable': it is private in 'java.util.Arrays'. ($$synthetic_jsr223_snippet_0.repl.kts:43:51)
```

### Kotlin 2.4.10

```
Runtime class of the binding: java.util.Arrays$ArrayList
2
```
