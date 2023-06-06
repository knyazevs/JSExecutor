class JSFunction(val jsCode: String, vararg args: String) {
    val args: Array<String> =  args.toList().toTypedArray()
}
