package coffeeshop.shared.utils

import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSCalendarUnitHour

actual fun getCurrentHour(): Int {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(NSCalendarUnitHour, NSDate())
    return components.hour
}
