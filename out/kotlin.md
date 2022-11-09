##

Java 是如何识别 Kotlin 的独有语法的呢

### Kotlin 的编译流程

你写出的 Kotlin 代码，电脑是无法直接理解的。即使是最简单的println("Hello world.")，你将这行代码告诉电脑，它也是无法直接运行的。这是因为，Kotlin 的语法是基于人类语言设计的，电脑没有人的思维，它只能理解二进制的 0 和 1，不能理解 println 到底是什么东西。



因此，Kotlin 的代码在运行之前，要先经过编译（Compile）。举个例子，假如我们现在有一个简单的 Hello World 程序：

``` kotlin
println("Hello world.")
```


经过编译以后，它会变成类似这样的东西：

``` 
LDC "Hello world."
INVOKESTATIC kotlin/io/ConsoleKt.println (Ljava/lang/Object;)V
```

上面两行代码，其实是 Java 的字节码。
对，你没看错，Kotlin 代码经过编译后，
最终会变成 Java 字节码。
这给人的感觉就像是：我说了一句中文，
编译器将其翻译成了英文。
而 Kotlin 和 Java 能够兼容的原因也在于此，
Java 和 Kotlin 本质上是在用同一种语言进行沟通。

英语被看作人类世界的通用语言，那么 Kotlin 和 Java 用的是什么语言呢？
没错，它们用的就是 Java 字节码。Java 字节码并不是为人类设计的语言，
它是专门给 JVM 执行的。


JVM，也被称作 Java 虚拟机，它拿到字节码后就可以解析出字节码的含义，
并且在电脑里输出打印“Hello World.”。
所以，你可以先把 Java 虚拟机理解为一种执行环境。
回想我们在第一节课开头所安装的 JDK，
就是为了安装 Java 的编译器和 Java 的运行环境。


那么，JVM 到底是如何让字节码运行起来的呢？其实，JVM 是建立在操作系统之上的一层抽象运行环境。举个简单的例子，Windows 系统当中的程序是无法直接在 Mac 上面运行的。但是，我们写的 Java 程序却能同时在 Windows、Mac、Linux 系统上运行，这就是因为 JVM 在其中起了作用。



### 如何研究 Kotlin？


因此，我们可以尝试另一种思路：将 Kotlin 转换成字节码后，再将字节码反编译成等价的 Java 代码。
最终，我们去分析等价的 Java 代码，通过这样的方式来理解 Kotlin 的实现细节。
虽然这种方式不及字节码那样深入底层，但它的好处是足够直观，也方便我们去分析更复杂的代码逻辑。



这个过程看起来会有点绕，让我们用一个流程图来表示：

``` java

println("Hello world.") /*
          编译
           ↓            */    
LDC "Hello world."
INVOKESTATIC kotlin/io/ConsoleKt.println (Ljava/lang/Object;)V  /*
         反编译
           ↓            */
String var0 = "Hello world.";
System.out.println(var0);

```


### 好了，思想和流程我们都清楚了，具体我们应该要怎么做呢？有以下几个步骤。

第一步，打开我们要研究的 Kotlin 代码。

第二步，依次点击菜单栏：Tools -> Kotlin -> Show Kotlin Bytecode。

这时候，我们在右边的窗口中就可以看见 Kotlin 对应的字节码了。但这并不是我们想要的，所以要继续操作，将字节码转换成 Java 代码。

这时候，我们在右边的窗口中就可以看见 Kotlin 对应的字节码了。但这并不是我们想要的，所以要继续操作，将字节码转换成 Java 代码。第三步，点击画面右边的“Decompile”按钮。


## Kotlin 里到底有没有“原始类型”？

那么现在，我们已经知道了 Kotlin 与 Java 直接存在某种对应关系，
所以要弄清楚这个问题，我们只需要知道“Kotlin 的 Long”与“Java long/Long”是否存在某种联系就可以了。



```kotlin

// kotlin 代码

// 用 val 定义可为空、不可为空的Long，并且赋值
val a: Long = 1L
val b: Long? = 2L

// 用 var 定义可为空、不可为空的Long，并且赋值
var c: Long = 3L
var d: Long? = 4L

// 用 var 定义可为空的Long，先赋值，然后改为null
var e: Long? = 5L
e = null

// 用 val 定义可为空的Long，直接赋值null
val f: Long? = null

// 用 var 定义可为空的Long，先赋值null，然后赋值数字
var g: Long? = null
g = 6L
```

``` java

// 反编译后的 Java 代码

long a = 1L;
long b = 2L;

long c = 3L;
long d = 4L;

Long e = 5L;
e = (Long)null;

Long f = (Long)null;

Long g = (Long)null;
g = 6L;
```


``` java 
可以看到，最终 a、b、c、d 被 Kotlin 转换成了 Java 的原始类型 long；
而 e、f、g 被转换成了 Java 里的包装类型 Long。
这里我们就来逐步分析一下：
```

对于变量 a、c 来说，它们两个的类型是不可为空的，
所以无论如何都不能为 null，对于这种情况，
Kotlin 编译器会直接将它们优化成原始类型。


对于变量 b、d 来说，它们两个的类型虽然是可能为空的，但是它的值不为 null，
并且，编译器对上下文分析后发现，这两个变量也没有在别的地方被修改。这种情况，Kotlin 编译器也会将它们优化成原始类型。


对于变量 e、f、g 来说，不论它们是 val 还是 var，
只要它们被赋值过 null，那么，Kotlin 就无法对它们进行优化了。
这背后的原因也很简单，Java 的原始类型不是对象，只有对象才能被赋值为 null。



我们可以用以下两个规律，来总结下 Kotlin 对基础类型的转换规则：

- 只要基础类型的变量可能为空，那么这个变量就会被转换成 Java 的包装类型。
- 反之，只要基础类型的变量不可能为空，那么这个变量就会被转换成 Java 的原始类型。

好，接着我们再来看看另外一个例子。


## 接口语法的局限性

我在上节课，带你了解了 Kotlin 面向对象编程中的“接口”这个概念，
其中我给你留了一个问题，就是：

``` 
接口的“成员属性”，是 Kotlin 独有的。请问它的局限性在哪？
 
```



``` kotlin

// Kotlin 代码

interface Behavior {
    // 接口内可以有成员属性
    val canWalk: Boolean

    // 接口方法的默认实现
    fun walk() {
        if (canWalk) {
            println(canWalk)
        }
    }
}

private fun testInterface() {
    val man = Man()
    man.walk()
}
```


``` java

// 等价的 Java 代码

public interface Behavior {
   // 接口属性变成了方法
   boolean getCanWalk();

   // 方法默认实现消失了
   void walk();

   // 多了一个静态内部类
   public static final class DefaultImpls {
      public static void walk(Behavior $this) {
         if ($this.getCanWalk()) {
            boolean var1 = $this.getCanWalk();
            System.out.println(var1);
         }
      }
   }
}
```


从上面的 Java 代码中我们能看出来，Kotlin 接口的“默认属性”canWalk，本质上并不是一个真正的属性，当它转换成 Java 以后，
就变成了一个普通的接口方法 getCanWalk()。


另外，Kotlin 接口的“方法默认实现”，
它本质上也没有直接提供实现的代码。
对应的，它只是在接口当中定义了一个静态内部类“DefaultImpls”，
然后将默认实现的代码放到了静态内部类当中去了。

**我们能看到，Kotlin 的新特性，最终被转换成了一种 Java 能认识的语法。**



```kotlin

// Kotlin 代码

class Man: Behavior {
    override val canWalk: Boolean = true
}
```

以上代码中，我们定义了一个 Man 类，它实现了 Behavior 接口，与此同时它也重写了 canWalk 属性。
另外，由于 Behavior 接口的 walk() 方法已经有了默认实现，所以 Man 可以不必实现 walk() 方法。

那么，Man 类反编译成 Java 后，会变成什么样子呢？

```java

// 等价的 Java 代码

public final class Man implements Behavior {
   private final boolean canWalk = true;

   public boolean getCanWalk() {
      // 关键点 ①
      return this.canWalk;
   }

   public void walk() {
      // 关键点 ②
      Behavior.DefaultImpls.walk(this);
   }
}
```


可以看到，Man 类里的 getCanWalk() 实现了接口当中的方法，
从注释①那里我们注意到，getCanWalk() 返回的还是它内部私有的 canWalk 属性，
这就跟 Kotlin 当中的逻辑“override val canWalk: Boolean = true”对应上了。
另外，对于 Man 类当中的 walk() 方法，
它将执行流程交给了“Behavior.DefaultImpls.walk()”，并将 this 作为参数传了进去。
这里的逻辑，就可以跟 Kotlin 接口当中的默认方法逻辑对应上来了。



Kotlin 接口当中的属性，在它被真正实现之前，本质上并不是一个真正的属性。

因此，Kotlin 接口当中的属性，它既不能真正存储任何状态，也不能被赋予初始值，因为它本质上还是一个接口方法。


### 思考题

```kotlin

class Person(val name: String, var age: Int) {
    val isAdult
        get() = age >= 18
}
```

下面这个是错误的。
``` kotlin

class Person(val name: String, var age: Int) {
    val isAdult = age >= 18
}
```

 

思考题：
转换成 java代码就一清二楚，两种方式的isAdult本质不是同一个东西：
1-通过自定义 getter 来实现的方式，isAdult其实是一个方法。外部每一次调用，都是拿最新的age进行计算，
所以age的值有变动，isAdult()的结果是最新的。
2- val isAdult = age >= 18 这种方式，isAdult是一个final变量，只会在对象新建时，在构造方法中，根据age的值赋值一次。
所以，之后age的值如果有变动，isAdault值是永远不变的。





``` java


class Person(val name: String, var age: Int) {
    val isAdult = age >= 18
}

public final class PersonA {
   private final boolean isAdult;
   @NotNull
   private final String name;
   private int age;

   public final boolean isAdult() {
      return this.isAdult;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final int getAge() {
      return this.age;
   }

   public final void setAge(int var1) {
      this.age = var1;
   }

   public PersonA(@NotNull String name, int age) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
      this.age = age;
      this.isAdult = this.age >= 18;
   }
}
```


``` java
public final class PersonB {
   @NotNull
   private final String name;
   private int age;

   public final boolean isAdult() {
      return this.age >= 18;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final int getAge() {
      return this.age;
   }

   public final void setAge(int var1) {
      this.age = var1;
   }

   public PersonB(@NotNull String name, int age) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
      this.age = age;
   }
}

```