package com.rsastack.toothpick

object DI {
    const val APP_SCOPE = "APP_SCOPE"
    const val SERVER_SCOPE = "SERVER_SCOPE"

    // Dinamic scope names, thay are changing in initUiScope()
    var TOP_FLOW_SCOPE = "TOP_FLOW_SCOPE"
    var AUTH_FLOW_SCOPE = "AUTH_FLOW_SCOPE"
}

val ARG_SCOPE_NAME = "arg_scopeName"
val ARG_PARENT_SCOPE_NAME = "arg_parentScopeName"
