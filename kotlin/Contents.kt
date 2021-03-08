import java.lang.IllegalArgumentException
import java.sql.Time
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.min

fun main(args: Array<String>) {
    // Частина 1

    // Дано рядок у форматі "Student1 - Group1; Student2 - Group2; ..."


    var studentsStr = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; " +
            "Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81;" +
            " Кочерук Давид - ІВ-83; Лихацька Юлія - ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; " +
            "Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81;" +
            " Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83;" +
            " Грабко Михайло - ІВ-81; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83;" +
            " Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82;" +
            " Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82"



    // Завдання 1
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – відсортований масив студентів, які відносяться до відповідної групи

    // Ваш код починається тут

    var studentsGroups = mutableMapOf<String, ArrayList<String>>()
    fun studentsToDic(): MutableMap<String, ArrayList<String>> {
        val splittedStudents = studentsStr.split(';')

        for (student in splittedStudents) {
            var del = " - "
            val everyStudent = student.split(del)
            val group = everyStudent[1]
            val studentsName = everyStudent[0]

            if (group in studentsGroups) {
                studentsGroups[group]?.add(studentsName)
            }
            else {
                studentsGroups[group] = arrayListOf<String>(studentsName)
            }
        }

        for ((k, v) in studentsGroups) {
            studentsGroups[k]?.sort()
        }
        return studentsGroups
    }

    studentsToDic()


    // Ваш код закінчується тут

    // Дано масив з максимально можливими оцінками

    val points: Array<Int> = arrayOf(12, 12, 12, 12, 12, 12, 12, 16)




    // Завдання 2
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – масив з оцінками студента (заповніть масив випадковими значеннями, використовуючи функцію `randomValue(maxValue: Int) -> Int`)

    fun randomValue(maxValue: Int): Int {
        return when(Random().nextInt(6)) {
            1 -> ceil(maxValue.toFloat() * 0.7).toInt()
            2 -> ceil(maxValue.toFloat() * 0.9).toInt()
            3,4,5 -> maxValue
            else -> 0
        }
    }

    var studentPoints: MutableMap<String, MutableMap<String, List<Int>>> = mutableMapOf()

    // Ваш код починається тут

    fun genStudentPoints() {
        for ((k, v) in studentsGroups) {
            studentPoints[k] = mutableMapOf<String, List<Int>>()
            for (student in v) {
                val everyStudentsPoints = points.map { randomValue(it) }
                studentPoints[k]?.put(student, everyStudentsPoints)
            }
        }
    }
    genStudentPoints()

    println(studentPoints)




    // Ваш код закінчується тут

    // Завдання 3
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – сума оцінок студента

    var sumPoints: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()

    // Ваш код починається тут

    fun studentsPointsSum() {
        for ((k, v) in studentPoints) {
            sumPoints[k] = mutableMapOf<String, Int>()
            for ((student, pointsList) in v) {
                sumPoints[k]?.put(student, pointsList.sum())
            }
        }
    }
    studentsPointsSum()
    println(sumPoints)

    // Ваш код закінчується тут

    // Завдання 4
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – середня оцінка всіх студентів групи

    var groupAvg: MutableMap<String, Float> = mutableMapOf()

    // Ваш код починається тут

    fun averageGroupPoints() {
        for ((k, v) in sumPoints) {
            var sum: Int = 0
            for (pointsSum in v.values) {
                sum += pointsSum
            }
            groupAvg[k] = sum.toFloat() / (points.size * v.size)
        }
    }

    averageGroupPoints()
    println(groupAvg)

    // Ваш код закінчується тут

    // Завдання 5
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – масив студентів, які мають >= 60 балів

    var passedPerGroup: MutableMap<String, ArrayList<String>> = mutableMapOf()

    // Ваш код починається тут

    fun studentsWhoPassed() {
        for ((k, v) in sumPoints) {
            passedPerGroup[k] = arrayListOf<String>()
            for ((student, pointsSum) in v) {
                if (pointsSum >= 60) {
                    passedPerGroup[k]?.add(student)
                }
            }
        }
    }

    studentsWhoPassed()
    println(passedPerGroup)

    // Ваш код закінчується тут

    val obj1 = TimeKY(Date(0,0,0,23,59,59))
    val obj2 = TimeKY()
    val obj3 = TimeKY(12u, 0u, 1u)
    val obj4 = TimeKY(0u, 0u, 0u)
    val obj5 = TimeKY(0u, 0u, 1u)
    val obj6 = TimeKY(12u, 55u, 1u)
    val obj7 = TimeKY(14u, 5u, 15u)
    println(obj4.getMultipleObjectTimeSum(obj6, obj7).getStringDate())
    println(obj4.getMultipleObjectTimeDiff(obj6, obj7).getStringDate())
}

class TimeKY() {

    companion object {
        fun addZeroFormat(timeValue: UInt): String {
            return if (timeValue < 10u) "0$timeValue" else "$timeValue"
        }

        fun resolveOverflowForSum(hours: UInt, minutes: UInt, seconds: UInt): TimeKY {
            var h = hours;
            var m = minutes;
            var s = seconds;
            if (s > 59u) {
                s %= 60u
                m += 1u
            }
            if (m > 59u) {
                m %= 60u
                h += 1u
            }
            if (h > 23u) {
                h %= 24u
            }
            return TimeKY(h, m, s)
        }

        fun resolveOverflowForDiff(hours: Int, minutes: Int, seconds: Int): TimeKY {
            var h = hours;
            var m = minutes;
            var s = seconds;

            if (s < 0) {
                s += 60
                m -= 1
            }
            if (m < 0) {
                m += 60
                h -= 1
            }
            if (h < 0) {
                h += 24
            }

            return TimeKY(h.toUInt(), m.toUInt(), s.toUInt())
        }

    }
    var hours: UInt = 0u
        set(value) {
            if (value.toInt() in (0..23)) {
                field = value
            }
            else {
                throw IllegalArgumentException("Hours out of 0..23 range")
            }
        }

    var minutes: UInt = 0u
        set(value) {
            if (value.toInt() in (0..59)) {
                field = value
            }
            else {
                throw IllegalArgumentException("Minutes out of 0..59 range")
            }
        }

    var seconds: UInt = 0u
        set(value) {
            if (value?.toInt() in (0..59)) {
                field = value
            }
            else {
                throw IllegalArgumentException("Seconds out of 0..59 range")
            }
        }

    constructor(_hours: UInt, _minutes: UInt, _seconds: UInt) : this() {
            hours = _hours
            minutes = _minutes;
            seconds = _seconds;
    }

    constructor(_date: Date) : this() {
        hours = _date.getHours().toUInt();
        minutes = _date.getMinutes().toUInt();
        seconds = _date.getSeconds().toUInt();
    }

    fun getStringDate(): String {
        var dayTime: String = "AM"

        when (hours.compareTo(12u)) {
            -1 -> dayTime = "AM"
            1 or 0 -> dayTime = "PM"
        }
        return "${TimeKY.Companion.addZeroFormat(hours)}:" +
                "${TimeKY.Companion.addZeroFormat(minutes)}:" +
                "${TimeKY.Companion.addZeroFormat(seconds)} $dayTime"
    }



    fun getTimeSumObject(timeObject: TimeKY): TimeKY {
        val h = this.hours + timeObject.hours
        val m = this.minutes + timeObject.minutes
        val s = this.seconds + timeObject.seconds

        return Companion.resolveOverflowForSum(h, m, s)
    }

    fun getTimeDiffObject(timeObject: TimeKY): TimeKY {
        var h = this.hours.toInt() - timeObject.hours.toInt()
        var m = this.minutes.toInt() - timeObject.minutes.toInt()
        var s = this.seconds.toInt() - timeObject.seconds.toInt()

        return Companion.resolveOverflowForDiff(h, m , s)
    }

    fun getMultipleObjectTimeSum(timeObject1: TimeKY, timeObject2: TimeKY): TimeKY {
        val h = timeObject1.hours + timeObject2.hours
        val m = timeObject1.minutes + timeObject2.minutes
        val s = timeObject1.seconds + timeObject2.seconds

        return Companion.resolveOverflowForSum(h, m, s)
    }

    fun getMultipleObjectTimeDiff(timeObject1: TimeKY, timeObject2: TimeKY): TimeKY {
        val h = timeObject1.hours.toInt() - timeObject2.hours.toInt()
        val m = timeObject1.minutes.toInt() - timeObject2.minutes.toInt()
        val s = timeObject1.seconds.toInt() - timeObject2.seconds.toInt()

        return Companion.resolveOverflowForDiff(h, m, s)
    }

}