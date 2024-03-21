package basic.config

abstract class Config {
    private var realConfig = 1
    fun initConfig() {
        realConfig = getConfig()
    }

    abstract fun getConfig(): Int
}