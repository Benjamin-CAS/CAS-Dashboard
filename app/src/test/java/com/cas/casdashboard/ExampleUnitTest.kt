package com.cas.casdashboard

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun main() {
        test()
        val a = A()
        val aList = ArrayList<List<D>>()
        for (item in a.a){
            aList.add(item.c.d)
        }
        val d = ArrayList<D>()
        for (item in aList){
            for (items in item){
                d.add(items)
            }
        }
        println("========")
        println(d)
        println("========")
    }
    data class A(
        val a:List<B> = listOf(B(C(listOf(D("1")))))
    )
    data class B(
        val c:C = C(listOf(D("12")))
    )
    data class C(
        val d:List<D> = listOf(D("121212"))
    )
    data class D(
        val e:String = "123232"
    )
    private fun test() = runBlocking {
        launch {
            async {
                useAsync()
            }
            async {
                useWithContext()
            }
        }
    }
    private suspend fun useAsync() = withContext(Dispatchers.IO) {
        measureTimeMillis {
            val a = async(Dispatchers.Default) {
                getOne()
            }

            val b = async(Dispatchers.Default) {
                getOne()
            }
            val c = async(Dispatchers.Default) {
                getOne()
            }
            println("useAsync get value = ${a.await() + b.await() + c.await()}")
        }.also {
            println("useAsync: 用时 $it ms")
        }
    }
    private suspend fun useWithContext() = withContext(Dispatchers.IO) {
        measureTimeMillis {
            val a = withContext(Dispatchers.Default) {
                getOne()
            }
            val b = withContext(Dispatchers.Default) {
                getOne()
            }
            val c = withContext(Dispatchers.Default) {
                getOne()
            }
            println("useWithContext get value = ${a + b + c}")
        }.also {
            println("useWithContext: 用时 $it ms")
        }
    }




    private suspend fun getOne(): Int {
        delay(1000)
        return 1
    }
}