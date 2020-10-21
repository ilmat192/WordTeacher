package com.aglushkov.wordteacher.shared.general.item

abstract class BaseViewItem<T> {
    var id = 0 // a unique id, required for NSDiffableDataSourceSnapshot
    var type = 0
    var items = listOf<T>()

    constructor(item: T, type: Int) {
        this.items = listOf(item)
        this.type = type
    }

    constructor(items: List<T>, type: Int) {
        this.items = items
        this.type = type
    }

    fun firstItem() = items.first()

    open fun equalsByIds(item: BaseViewItem<*>): Boolean {
        // by default we compare the content as we don't have ids here
        // if an id is available in a subclass this check should be used:
        //     this.javaClass == item.javaClass && type == item.type && id == item.id
        // and equalsByContent should be overridden too
        return id == item.id && type == item.type
    }

    open fun equalsByContent(other: BaseViewItem<*>): Boolean {
        // as we check the content in equalsByIds we can return true here
        // this should be overridden if equalsByIds is overridden
        return equals(other)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseViewItem<*>) return false

        if (type != other.type) return false
        if (items != other.items) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + items.hashCode()
        result = 31 * result + id
        return result
    }

    fun itemsHashCode(): Int = this.items.hashCode()
    fun itemsEquals(items: List<T>) = this.items.equals(items)

    companion object {}
}