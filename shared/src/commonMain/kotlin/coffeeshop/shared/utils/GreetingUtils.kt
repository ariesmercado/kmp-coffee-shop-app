package coffeeshop.shared.utils

//expect fun getCurrentHour(): Int

fun getGreetingForTime(hour: Int): String {
    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..21 -> "Good Evening"
        else -> "Hello"
    }
}

fun getPersonalizedGreeting(userName: String): String {
//    val hour = getCurrentHour()
//    val greeting = getGreetingForTime(hour)
    return "Hi, $userName!"
}
