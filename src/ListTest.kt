import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ListTest {

//    val list = List.Cons(1,2,3,4,5,6,7,8,9,10)
    @Test
    fun dropWhile() {
//        val listFrop = list.dropWhile { it < 5 }
//        println(listFrop)
    }

    @Test
    fun whenCountIs3(){
        val list = List(3,3,3)
        assertEquals(3, list.count())
    }
    @Test
    fun whenCountIs0(){
        val list = List<Int>()
        assertEquals(0, list.count())
    }
}