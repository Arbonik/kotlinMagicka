sealed class List<A> {
    companion object {
        fun <A> init(list: List<A>): List<A> = list.reverseRight().drop(1).reverseRight()

        fun <A> reverseRight(acc: List<A>, list: List<A>): List<A> {
            return when (list) {
                is Cons -> reverseRight(acc.cons(list.head), list.tail)
                Nil -> acc
            }
        }

        tailrec fun <A> dropWhile(list: List<A>, p: (A) -> Boolean): List<A> {
            return when (list) {
                is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
                Nil -> list
            }
        }

        fun <A> concat(list1: List<A>, list2: List<A>): List<A> {
            return when (list1) {
                Nil -> list2
                is Cons -> concat(list1.tail, list2).cons(list1.head)
            }
        }

        operator
        fun <A> invoke(vararg az: A) =
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }

        tailrec fun <A, B> foldLeft(
            acc: B,
            list: List<A>,
            f: (B) -> (A) -> B
        ): B = when (list) {
            is Cons -> foldLeft(f(acc)(list.head), list.tail, f)
            Nil -> acc
        }

    }

    fun length(): Int = foldLeft(0, this) {
        { _ -> it + 1 }
    }

    fun product(list: List<Double>): Double = foldLeft(1.0, list) { x ->
        { y -> x * y }
    }

    fun sum(list: List<Int>): Int = foldLeft(0, list) { x -> { y -> x + y } }

    fun <B> foldLeft(acc: B, f: (B) -> (A) -> B) = Companion.foldLeft(acc, this, f)

//    fun <A> reverseLeft(): List<A> =
//        foldLeft(List.invoke()) { acc -> { acc.cons(it) } }


    abstract fun isEmpty(): Boolean

    fun cons(a: A): List<A> {
        return Cons(a, this)
    }

    fun drop(n: Int): List<A> {
        tailrec fun drop(l: Int, list: List<A>): List<A> =
            if (l <= 0) list else when (list) {
                is Cons -> drop(l - 1, list.tail)
                Nil -> this
            }
        return drop(n, this)
    }


    fun count(): Int = foldRight(this, 0) { { b -> b + 1 } }


    fun dropWhile(p: (A) -> Boolean): List<A> = Companion.dropWhile(this, p)
    fun concat(list: List<A>): List<A> = concat(this, list)
    fun reverseRight(): List<A> = reverseRight(List.invoke(), this)


    abstract fun setHead(a: A): List<A>

    internal object Nil : List<Nothing>() {
        override fun isEmpty() = true
        override fun toString() = "[NIL]"
        override fun setHead(a: Nothing): List<Nothing> {
            throw Exception("i havent hhead")
        }
    }

    internal class Cons<A>(internal val head: A, internal val tail: List<A>) : List<A>() {
        override fun isEmpty() = false

        override fun toString(): String = "[${toString("", this)}NIL]"

        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> {
                    acc
                }
                is Cons -> {
                    toString("$acc${list.head}", list.tail)
                }
            }

        override fun setHead(a: A): List<A> {
            return tail.cons(a)
        }
    }
}