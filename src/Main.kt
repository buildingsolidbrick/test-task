import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class User(val name: String, val sex: String, val birthday: LocalDate, val phone: String?, val salary: Int?) {

}

val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")


fun main() {
    val dataLines = File("data.txt").readLines()
    val users = getAllUsersObj(dataLines)
    printUsers(users)
    printCountGender(users)
    printUserFilterByAge(25, users)
    printAverageSalary(users)
    printCountWomenWithPhone(users)
}


fun getAllUsersObj(dataLines: List<String>): List<User> {
    val users = mutableListOf<User>()
    for (dataLine in dataLines) {
        val dataSplit = dataLine.split(";")
        val birthday = LocalDate.parse(dataSplit[2], formatter)
        val user = User(dataSplit[0], dataSplit[1], birthday, dataSplit[3].ifBlank { null }, dataSplit[4].toIntOrNull())
        users.add(user)
    }
    return users
}

fun printUsers(users: List<User>) {
    println("Все объекты User:")
    for (user in users) {
        println(user)
    }
}

fun printCountGender(users: List<User>) {
    println("Кол-во мужчин: ${users.count { it.sex == "муж" }}")
    println("Кол-во женщин: ${users.count { it.sex == "жен" }}")
}

fun printUserFilterByAge(findAge: Int, users: List<User>) {
    println(
        "Кол-во людей возратом > 25: ${
            users.count {
                (LocalDate.now().toEpochDay() - it.birthday.toEpochDay()) / 365 > 25
            }
        }"
    )
}

fun printAverageSalary(users: List<User>) {
    var accumulator = 0
    for (user in users) {
        if (user.salary != null) accumulator+=user.salary
    }
    println("Средняя з/п: ${accumulator/users.count { it.salary != null}}")
}

fun printCountWomenWithPhone(users: List<User>) {
    println("Кол-во женщин с номерами телефона: ${users.count{ it.sex == "жен" && it.phone != null}}")
}