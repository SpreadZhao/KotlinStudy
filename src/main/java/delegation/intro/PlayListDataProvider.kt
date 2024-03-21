package delegation.intro

class PlayListDataProvider : IPLDataProvider<Video> {

    private val _mData = ArrayList<Video>()

    override fun queryData() {
        _mData.addAll(fakeQueryFromNet())
    }

    override fun getList() = _mData

    private fun fakeQueryFromNet(): List<Video> {
        return listOf(
            Video(name = "因为一个bug，程序员在出租屋内结束自己的生命", duration = 1093, authorId = 123235235),
            Video(name = "Java第一次作业", duration = 125315, authorId = 3485927523875),
            Video(name = "你们使用的手机终端有哪些？", duration = 235235, authorId = 235235236236)
        )
    }
}