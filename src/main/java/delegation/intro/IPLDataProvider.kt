package delegation.intro

interface IPLDataProvider<T> {
    fun queryData()
    fun getList(): List<T>
}