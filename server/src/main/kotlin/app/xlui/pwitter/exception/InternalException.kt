package app.xlui.pwitter.exception

class InternalException : RuntimeException {
    constructor() : super()
    constructor(msg: String) : super(msg)
    constructor(msg: String, cause: Throwable) : super(msg, cause)
}