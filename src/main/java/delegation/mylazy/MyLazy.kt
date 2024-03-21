package delegation.mylazy

import kotlin.reflect.KProperty

class MyLazy<T>(val block: () -> T) {

    var value: T? = null

    operator fun getValue(myClass: Any?, prop: KProperty<*>): T {
        if (value == null) value = block()
        return value!!
    }
}

fun <T> myLazy(block: () -> T) = MyLazy(block)