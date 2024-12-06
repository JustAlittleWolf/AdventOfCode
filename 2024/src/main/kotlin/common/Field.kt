package me.wolfii.common

import kotlin.collections.any

interface Field<T> {
    val width: Int
    val height: Int
    val indices: Collection<Vec2I>

    operator fun get(pos: Vec2I): T? = get(pos.x, pos.y)
    operator fun get(x: Int, y: Int): T? = rows().getOrNull(y)?.getOrNull(x)

    fun isInside(pos: Vec2I): Boolean = isInside(pos.x, pos.y)
    fun isInside(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < width && y < height

    fun rows(): List<List<T>>
}

interface MutableField<T> : Field<T> {
    operator fun set(pos: Vec2I, value: T) = set(pos.x, pos.y, value)
    operator fun set(x: Int, y: Int, value: T)
}

class FieldImpl<T>(private val rows: List<List<T>>) : Field<T> {
    override val height = rows.size
    override val width = if (height == 0) 0 else rows[0].size

    init {
        require(rows.all { it.size == width }) { "field has to be a rectangle" }
    }

    override val indices = (0..<width).flatMap { x -> (0..<height).map { y -> Vec2I(x, y) } }

    override fun rows() = rows
}

class MutableFieldImpl<T>(private val rows: List<MutableList<T>>) : MutableField<T> {
    override val height = rows.size
    override val width = if (height == 0) 0 else rows[0].size

    override val indices = (0..<width).flatMap { x -> (0..<height).map { y -> Vec2I(x, y) } }

    override fun rows() = rows

    override fun set(x: Int, y: Int, value: T) {
        rows[y][x] = value
    }
}

fun List<String>.toCharField(): Field<Char> = FieldImpl(this.map { it.toCharArray().filterNot(Char::isWhitespace) })
fun List<String>.toMutableCharField(): MutableField<Char> = MutableFieldImpl(this.map { it.toCharArray().filterNot(Char::isWhitespace).toMutableList() })

fun <T> List<List<T>>.toField(): Field<T> = FieldImpl(this)

fun <T> List<List<T>>.toMutableField(): MutableField<T> {
    val height = this.size
    if (height == 0) return MutableFieldImpl(listOf())
    val width = this[0].size
    if (this.any { it.size != width }) throw IllegalArgumentException("Field was not a rectangle.")
    return MutableFieldImpl<T>(this.map { row -> row.mapTo(ArrayList<T>()) { it } })
}

fun <T> Field<T>.toMutableField(): MutableField<T> {
    return MutableFieldImpl<T>(this.rows().map { row -> row.mapTo(ArrayList<T>()) { it } })
}

fun <T, R> Field<T>.map(transform: (T) -> R): Field<R> {
    return FieldImpl(rows().map { row -> row.map(transform) })
}

fun <T, R> Field<T>.mapIndexed(transform: (Vec2I, T) -> R): Field<R> {
    return FieldImpl(rows().mapIndexed { y, row -> row.mapIndexed { x, value -> transform(Vec2I(x, y), value) } })
}
