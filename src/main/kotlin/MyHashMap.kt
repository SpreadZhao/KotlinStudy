class MyHashMap<K, V> {
    class Entry<K, V>(
        var k: K? = null,
        var v: V? = null,
        var next: Entry<K, V>? = null
    )

    companion object {
        const val DEFAULT_CAPACITY = 16
        const val DEFAULT_LOAD_FACTOR = 0.75f
        private fun upperMinPowerOf2(n: Int): Int {
            var power = 1
            while (power <= n) {
                power *= 2
            }
            return power
        }

        fun test() {
            val myMap = MyHashMap<Int, String>()
            for (i in 1..10) {
                myMap.put(i, "key$i")
            }
            println("My map size: ${myMap.size}")
            for (i in 1..10) {
                println("key: $i, value: ${myMap.get(i)}")
            }
            myMap.remove(7)
            println("After remove:")
            for (i in 1..10) {
                println("key: $i, value: ${myMap.get(i)}")
            }
        }
    }

    private var capacity = 0
    private var loadFactor = 0f
    private var _size = 0
    private var table: Array<Entry<K, V>?>
    val size: Int get() = _size

    constructor() : this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR)

    constructor(capacity: Int, loadFactor: Float) {
        this.capacity = upperMinPowerOf2(capacity)
        this.loadFactor = loadFactor
        this.table = Array(capacity) { null }
    }

    fun put(k: K, v: V): V? {
        val index = k.hashCode() % table.size
        var current = table[index]
        if (current != null) {
            while (current != null) {
                if (current.k == k) {
                    val oldValue = current.v
                    current.v = v
                    return oldValue
                }
                current = current.next
            }
            table[index] = Entry(k, v, table[index])
            _size++
            return null
        }
        table[index] = Entry(k, v, null)
        _size++
        return null
    }

    fun get(k: K): V? {
        val index = k.hashCode() % table.size
        var current = table[index]
        while (current != null) {
            if (current.k == k) return current.v
            current = current.next
        }
        return null
    }

    fun remove(k: K): V? {
        val index = k.hashCode() % table.size
        val result: V?
        var current = table[index]
        var pre: Entry<K, V>? = null
        while (current != null) {
            if (current.k == k) {
                result = current.v
                _size--
                if (pre != null) {
                    pre.next = current.next
                } else {
                    table[index] = current.next
                }
                return result
            }
            pre = current
            current = current.next
        }
        return null
    }

    fun isEmpty() = size == 0
}