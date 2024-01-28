package basic

class BackingField {
    var age: Int = 0
        set(value) {
            field = value       // 设置自己的成员
            doOthers()          // 做一些其它的事情
        }

    private fun doOthers() {
        // something
    }
}