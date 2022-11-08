class Person1 {
}




// 计算机的世界里当然没有“人”的概念，但是这并不妨碍我们在代码当中定义一个人的“抽象模型”。
// 上面的 Person 类当中，有一个属性，叫做“name”，每个人都会有名字，而名字也是属于人的一部分。这也很好理解，对吧。
class Person(val name: String, var age: Int)


// Kotlin 定义的类，在默认情况下是 public 的，编译器会帮我们生成“构造函数”，对于类当中的属性，Kotlin 编译器也会根据实际情况，自动生成 getter 和 setter。


// 自定义属性 getter


// 也就是 age≥18。



class Person2(val name: String, var age: Int) {
    fun isAdult(): Boolean {
        return age >= 18
    }
}



class Person3(val name: String, var age: Int) {
    fun isAdult() = age >= 18
}

//我们还可以用另一种更符合直觉的写法，那就是将 isAdult() 定义成 Person 的属性。具体的做法，就是借助 Kotlin 属性的自定义 getter。


class Person4(val name: String, var age: Int) {
    val isAdult
        get() = age >= 18
//        ↑
//    这就是isAdult属性的getter方法
}



class Person5(val name: String, var age: Int) {
    val isAdult: Boolean
        get() {
            // do something else
            return age >= 18
        }
}

//首先，从语法的角度上来说，是否为成年人，
// // 本来就是属于人身上的一种属性。我们在代码当中将其定义为属性，
// 更符合直觉。而如果我们要给 Person 增加一个行为，比如 walk，那么这种情况下定义一个新的方法就是非常合适的。




// 这一段不行
class Person6(val name: String, var age: Int) {
    val isAdult = age >= 18
}

// 但实际上，这种代码是无法正常工作的。由于它牵涉到 Kotlin 的原理，你可以在学完下一节“Kotlin 原理”之后，再回过头来看看这段代码为什么有问题。



// 自定义属性 setter




class Person8(val name: String) {
    var age: Int = 0
        //  这就是age属性的setter
//       ↓
        set(value: Int) {
            //log(value)
            field = value
        }
    // 省略
}


// private
class Person9(val name: String) {
    var age: Int = 0
        private set(value: Int) {
            //log(value)
            field = value
        }
    // 省略
}

// 抽象类与继承


/*

//                      Java 的继承
//                           ↓
public class MainActivity extends Activity {
    @Override
    void onCreate(){ ... }
}

//              Kotlin 的继承
//                 ↓
class MainActivity : AppCompatActivity() {
    override fun onCreate() { ... }
}
 */





// 报错
//class Boy: Person() {
//}

// Kotlin 的类，默认是不允许继承的，除非这个类明确被 open 关键字修饰了。
// 另外，对于被 open 修饰的普通类，它内部的方法和属性，默认也是不允许重写的，除非它们也被 open 修饰了：

/*

open class Person() {
    val canWalk: Boolean = false
    fun walk()
}

class Boy: Person() {
    // 报错
    override val canWalk: Boolean = true
    // 报错
    override fun walk() {
    }
}
 */


// ，在继承的行为上面，Kotlin 和 Java 完全相反。Java 当中，一个类如果没有被 final 明确修饰的话，它默认就是可以被继承的

// Java 的继承是默认开放的，Kotlin 的继承是默认封闭的。



// 而在这里，我们又会发现 Kotlin 和 Java 不同的小细节：Kotlin 的继承和接口实现语法是一样的。多么得贴心！



// Kotlin 的接口，跟 Java 最大的差异就在于，接口的方法可以有默认实现，同时，它也可以有属性


interface Behavior {
    // 接口内的可以有属性
    val canWalk: Boolean

    // 接口方法的默认实现
    fun walk() {
        if (canWalk) {
            // do something
        }
    }
}

class Person10(val name: String): Behavior {
    // 重写接口的属性
    override val canWalk: Boolean
        get() = true
}


// 嵌套


class A {
    class B {
    }
}

// 以上代码中，B 类，就是 A 类里面的嵌套类，这非常容易理解。不过我们需要注意的是，
// 这种写法的嵌套类，我们无法在 B 类当中访问 A 类的属性和成员方法。


// 所以，Kotlin 当中的普通嵌套类，它的本质是静态的。相应地，如果想在 Kotlin 当中定义一个普通的内部类，我们需要在嵌套类的前面加上 inner 关键字。

//  在默认情况下，嵌套类变成了静态内部类

// 。只有当我们真正需要访问外部类成员的时候，我们才会加上 inner 关键字

// Kotlin 中的特殊类

// 数据类

// 就是用于存放数据的类。要定义一个数据类，我们只需要在普通的类前面加上一个关键字“data”即可。比如前面案例当中的 Person 类，
// 我们只需要在它的前面加上 data，就可以将它变为一个“数据类”。



// 数据类当中，最少要有一个属性
//↓

data class Person11(val name: String, val age: Int)

/*
在 Kotlin 当中，编译器会为数据类自动生成一些有用的方法。
它们分别是：equals()；hashCode()；toString()；componentN() 函数；copy()。
 */

fun double(x: Int): Int{
    return x
}

fun testPerson(x: Int): Int{
    val tom = Person("Tom", 18)
    val jack = Person("Jack", 19)
    println(tom.equals(jack)) // 输出：false
    println(tom.hashCode())   // 输出：对应的hash code
    println(tom.toString())   // 输出：Person(name=Tom, age=18)

//    val (name, age) = tom     // name=Tom, age=18
//    println("name is $name, age is $age .")
//
//    val mike = tom.copy(name = "Mike")
//    println(mike)             // 输出：Person(name=Mike, age=18)
    return 0
}



enum class Human {
    MAN, WOMAN
}

fun isMan(data: Human) = when(data) {
    Human.MAN -> true
    Human.WOMAN -> false
    // 这里不需要else分支，编译器自动推导出逻辑已完备
}

// 想要定义密封类，我们需要使用 sealed 关键字，它的中文含义也代表着“密封”。在 Android 开发当中，我们会经常使用密封类对数据进行封装。比如我们可以来看一个代码例子：



sealed class Result<out R> {
    data class Success<out T>(val data: T, val message: String = "") : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    data class Loading(val time: Long = System.currentTimeMillis()) : Result<Nothing>()
}


/*
这个密封类，我们是专门用于封装网络请求结果的。可以看到，在 Result 类当中，分别有三个数据类，分别是 Success、Error、Loading。我们将一个网络请求结果也分为了三大类，分别代表请求成功、请求失败、请求中。这样，当网络请求有结果以后，我们的 UI 展示逻辑就会变得非常简单，也就是非常直白的三个逻辑分支：成功、失败、进行中。我们将其与 Kotlin 协程当中的 when 表达式相结合，就能很好地处理 UI 展示逻辑：如果是 Loading，我们就展示进度条；如果是 Success，我们就展示成功的数据；如果是 Error，我们就展示错误提示框。

fun display(data: Result) = when(data) {
    is Result.Success -> displaySuccessUI(data)
    is Result.Error -> showErrorMsg(data)
    is Result.Loading -> showLoading()
}
 */


/*
在这节课当中，我们学习了面向对象常见的概念，
包括类、继承、接口、实现、枚举，还有 Kotlin 独有的数据类、密封类。
同时也进一步领略到了 Kotlin 语法在一些细节的良苦用心。
比如说：Kotlin 的类，默认是 public 的。Kotlin 的类继承语法、接口实现语法，是完全一样的。
Kotlin 当中的类默认是对继承封闭的，类当中的成员和方法，默认也是无法被重写的。这样的设计就很好地避免了继承被滥用。
Kotlin 接口可以有成员属性，还可以有默认实现。Kotlin 的嵌套类默认是静态的，这种设计可以防止我们无意中出现内存泄漏问题。
Kotlin 独特的数据类，在语法简洁的同时，还给我们提供了丰富的功能。密封类，作为枚举和对象的结合体，帮助我们很好地设计数据模型，
支持 when 表达式完备性。
 */

/*
在课程中，我提到了 Kotlin 接口的“成员属性”是存在一定的局限性的。那么，请问你能想到，它的局限性在哪里吗？

 密封类是子类固定，枚举是对象固定 吧

 */

