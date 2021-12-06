fun sum(list: List<Int>): Int = foldRight(list, 0) { a -> { b -> a + b } }

fun product(list: List<Double>): Double = foldRight(list, 1.0) { a -> { b -> a * b } }

fun <A, B> foldRight(
    list: List<A>,
    identify: B,
    f: (A) -> (B) -> B
): B = when (list) {
    is List.Cons -> f(list.head)(foldRight(list.tail, identify, f))
    List.Nil -> identify
}

fun main() {
    var l = List(10.0, 4.0)
    println(product(l))
}