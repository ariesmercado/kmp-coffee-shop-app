package coffeeshop.shared.utils

import java.util.Calendar

actual fun getCurrentHour(): Int {
    return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
}
