package delegation.intro


class DelegationTest {
    companion object {
        fun test() {
            val playListProvider = ProviderWrapper(PlayListDataProvider())
            playListProvider.queryData()
            println(playListProvider.getList())
            val relativeListDataProvider = ProviderWrapper<Video>(RelativeListDataProvider())
            relativeListDataProvider.queryData()
            println(relativeListDataProvider.getList())
        }

        fun testJava() {
            val playListProvider = ProviderWrapperJava(PlayListDataProvider())
            playListProvider.queryData()
            println(playListProvider.getList())
            val relativeListDataProvider = ProviderWrapperJava<Video>(RelativeListDataProvider())
            relativeListDataProvider.queryData()
            println(relativeListDataProvider.getList())
        }
    }
}