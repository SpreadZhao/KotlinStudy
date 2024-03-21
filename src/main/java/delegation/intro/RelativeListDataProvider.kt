package delegation.intro

class RelativeListDataProvider : IPLDataProvider<Video> {

    private val _mData = ArrayList<Video>()

    override fun queryData() {
        _mData.addAll(fakeQueryFromNet())
    }

    override fun getList() = _mData

    private fun fakeQueryFromNet(): List<Video> {
        return listOf(
            Video(name = "相关视频1", duration = 1093, authorId = 123235235),
            Video(name = "相关视频2", duration = 125315, authorId = 3485927523875),
            Video(name = "相关视频3", duration = 235235, authorId = 235235236236)
        )
    }

}