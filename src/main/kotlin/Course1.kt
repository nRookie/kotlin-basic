class Course1 {
}



fun course1(args: Int) {
    println("Hello World!")
    var price : Int = 100;
    var price1 = 100

    val i : Double = 1.toDouble()


    // 这是因为 Kotlin 强制要求开发者在定义变量的时候，指定这个变量是否可能为 null。

    //val i: Double = null // 编译器报错val
    val j: Double? = null // 编译通过

    // 这就意味着，“可能为空的变量”无法直接赋值给“不可为空的变量”，当然，反向赋值是没有问题的。

    var i1: Double = 1.0
    var j2: Double? = null


    // 数字类型


    val int = 1
    val long = 1234567L
    val double = 13.14
    val float = 13.14F
    val hexadecimal = 0xAF
    val binary = 0b01010101

    /* 整数默认会被推导为“Int”类型；
       Long 类型，我们则需要使用“L”后缀；
       小数默认会被推导为“Double”， 我们不需要使用“D”后缀；
       Float 类型，我们需要使用“F”后缀；
       使用“0x”，来代表十六进制字面量；
       使用“0b”，来代表二进制字面量。
     */

    // Java 可以隐式转换数字类型，而 Kotlin 更推崇显式转换。

    /*
    val i = 100
    val j: Long = i // 编译器报错
    */

    /*

        val i = 100
        val j: Long = i.toLong() // 编译通过
     */


    // 布尔类型

    // “&”代表“与运算”；, “|”代表“或运算”； “!”代表“非运算”；  “&&”和“||”分别代表它们对应的“短路逻辑运算”。

    //
    val i3 = 1
    val j3 = 2
    val k3 = 3

    val isTrue: Boolean = i3 < j3 && j3 < k3

    // 字符串：String


    val s = "Hello Kotlin!"

    val name = "Kotlin"
    print("Hello $name!")

    val array = arrayOf("Java", "Kotlin")
    print("Hello ${array.get(1)}!")
    /* ↑ 复杂的变量，使用${}*/// 输出结果：Hello Kotlin!

    /*
    Kotlin 还新增了一个原始字符串，是用三个引号来表示的。它可以用于存放复杂的多行文本，
    并且它定义的时候是什么格式，最终打印也会是对应的格式。所以当我们需要复杂文本的时候，
    就不需要像 Java 那样写一堆的加号和换行符了。
     */


    val s1 = """
       当我们的字符串有复杂的格式时
       原始字符串非常的方便
       因为它可以做到所见即所得。 """

    print(s1)


    // 数组


    val array2 = arrayOf("apple", "pear")
    println("Size is ${array2.size}")
    println("First element is ${array2[0]}")

    // 就比如说，以上代码中，我们直接使用 array.size 就能拿到数组的长度。
//    // 输出结果：
//    Size is 2
//    First element is apple


    // 函数声明

    // 函数调用

    helloFunction("Kotlin")

    // 不过，Kotlin 提供了一些新的特性，那就是命名参数。简单理解，就是它允许我们在调用函数的时候传入“形参的名字”。

    helloFunction(name = "Kotlin1")

    // 流程控制

    // if

    val i4 = 1

    if (i4 > 0) {
        print("Big")
    } else {
        print("Small")
    }


    val i5 = 1
    val message = if (i5 > 0) "Big" else "Small"

    // when
    // when 语句，在程序当中主要也是用于逻辑判断的。当我们的代码逻辑只有两个分支的时候，我们一般会使用 if/else，而在大于两个逻辑分支的情况下，我们使用 when。

    //
    val i6: Int = 1

    when(i6) {
        1 -> print("一")
        2 -> print("二")
        else -> print("i 不是一也不是二")
    }

    // 它同时也可以作为表达式，为变量赋值，如下所示


    val i7: Int = 1

    val message2 = when(i7) {
        1 -> "一"
        2 -> "二"
        else -> "i 不是一也不是二" // 如果去掉这行，会报错
    }


    // 循环迭代：while 与 for


    var i8 = 0
    while (i8 <= 2) {
        println(i8)
        i8++
    }

    var j8 = 0
    do {
        println(j8)
        j8++
    } while (j8 <= 2)


    // 但是对于 for 语句，Kotlin 和 Java 的用法就明显不一样了。

    //

    val array1 = arrayOf(1, 2, 3)
    for (i in array1) {
        println(i)
    }


    val oneToThree = 1..3 // 代表 [1, 3]

    for (i in 6 downTo 0 step 2) { println(i)}

    val (aLong, aString) = 42L to "Kotlin is awesome!"
    print(message)
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
}
// val 声明的变量，我们叫做不可变变量，它的值在初始化以后就无法再次被修改，它相当于 Java 里面的 final 变量。
// var 声明的变量，我们叫做可变变量，它对应 Java 里的普通变量。


// 基础类型 包括我们常见的数字类型、布尔类型、字符类型，以及前面这些类型组成的数组。


// 一切都是对象

// 基础类型分为原始类型（Primitive Types）和包装类型（Wrapper Type）。比如，整型会有对应的 int 和 Integer，前者是原始类型，后者是包装类型。

// Java 之所以要这样做，是因为原始类型的开销小、性能高，但它不是对象，无法很好地融入到面向对象的系统中。

// 然而，在 Kotlin 语言体系当中，是没有原始类型这个概念的。这也就意味着，在 Kotlin 里，一切都是对象。



// 空安全

// val i: Double = null // 编译器报错


// 函数生命

/*
关键字    函数名          参数类型   返回值类型
 ↓        ↓                ↓       ↓      */
fun helloFunction(name: String): String {
    return "Hello $name !"
}/*   ↑
   花括号内为：函数体
*/


/*
可以看到，在这段代码中：
使用了 fun 关键字来定义函数；函数名称，
使用的是驼峰命名法（大部分情况下）；函数参数，
是以 (name: String) 这样的形式传递的，
这代表了参数类型为 String 类型；返回值类型，紧跟在参数的后面；
最后是花括号内的函数体，它代表了整个函数的逻辑。

 ##

 另外你可以再注意一个地方，前面代码中的 helloFunction 函数，
 它的函数体实际上只有一行代码。那么针对这种情况，
 我们其实就可以省略函数体的花括号，
 直接使用“=”来连接，将其变成一种类似变量赋值的函数形式：
 */


fun helloFunctionOneLine(name: String): String = "Hello $name !"

// 这种写法，我们称之为单一表达式函数。需要注意的是，在这种情况下，表达式当中的“return”是需要去掉的。



// 为此，Kotlin 针对这种情况就提供了一种简写，叫做 Elvis 表达式。
fun getLength(text: String?): Int {
    return if (text != null) text.length else 0
}


// 虽然 Kotlin 在语法层面摒弃了“原始类型”，但有时候为了性能考虑，我们确实需要用“原始类型”？
//
//使用非空“原始类型”，编译器会自动编译成Java的原始类型。

