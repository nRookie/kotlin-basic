

class PersonA(val name: String, var age: Int) {
    val isAdult = age >= 18
}


class PersonB(val name: String, var age: Int) {
    val isAdult get() = age >= 18
}