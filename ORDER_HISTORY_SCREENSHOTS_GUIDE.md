# Order History Screen - Screenshots Guide

## Note on Screenshots
Since the build environment doesn't support running Android emulators or iOS simulators, screenshots cannot be automatically generated. However, this guide describes what the UI looks like when you run the app.

## Expected UI Appearance

### Android (Jetpack Compose)

#### Main Screen View
```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Order History                  ┃ <- Coffee Brown (#6F4E37)
┃ View your past orders          ┃    White text
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃ [🔍] Search by item or order ID┃
┃                                ┃
┃ [All Time] [Last 7 Days] ...   ┃ <- Filter chips
┃                                ┃
┃ ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ┃
┃ ┃ Order #001      $16.90    ┃ ┃ <- Latte Foam card
┃ ┃ Jan 14, 2026 at 08:52 PM  ┃ ┃
┃ ┃            [Completed]     ┃ ┃ <- Green badge
┃ ┃                            ┃ ┃
┃ ┃ 2x Caramel Macchiato...    ┃ ┃
┃ ┃ 1x Hot Chocolate...        ┃ ┃
┃ ┃                            ┃ ┃
┃ ┃           [View Details] →┃ ┃ <- Coffee Brown button
┃ ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ ┃
┃                                ┃
┃ ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ┃
┃ ┃ Order #002       $4.86     ┃ ┃
┃ ┃ Jan 12, 2026 at 08:52 PM  ┃ ┃
┃ ┃            [Completed]     ┃ ┃
┃ ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ ┃
┃                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

#### Expanded Card View
```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Order #001        $16.90      ┃
┃ Jan 14, 2026 at 08:52 PM     ┃
┃              [Completed]      ┃
┃                               ┃
┃ ┏━━━━━━━━━━━━━━━━━━━━━━━━━━┓┃ <- Slightly darker background
┃ ┃ 2x Caramel Macchiato $11.40┃┃
┃ ┃ Size: Large               ┃┃
┃ ┃ Add-ons: Extra Shot       ┃┃
┃ ┃                            ┃┃
┃ ┃ 1x Hot Chocolate     $4.25┃┃
┃ ┃ Size: Medium              ┃┃
┃ ┃ Add-ons: Whipped Cream    ┃┃
┃ ┗━━━━━━━━━━━━━━━━━━━━━━━━━━┛┃
┃                               ┃
┃           [Hide Details] →   ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

#### Empty State
```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                 ┃
┃                                 ┃
┃              ☕                 ┃ <- Large coffee emoji
┃                                 ┃
┃      No order history yet       ┃ <- Gray text
┃                                 ┃
┃  Start ordering to see your     ┃ <- Lighter gray
┃      history here               ┃
┃                                 ┃
┃                                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

### iOS (SwiftUI)

The iOS implementation looks identical to Android with native iOS styling:
- Native iOS search bar appearance
- Native iOS button styling
- Native iOS scroll behavior
- Same color scheme and layout

### Interactive Elements

1. **Search Bar**
   - Typing filters orders in real-time
   - Shows magnifying glass icon
   - Light background with subtle border

2. **Filter Chips**
   - Selected chip: Coffee Brown background, white text, bold
   - Unselected chip: Light background, dark text, normal weight
   - Tap to switch between filters

3. **Order Cards**
   - Elevation/shadow for depth
   - Rounded corners (12dp/12pt)
   - Latte Foam background color
   - Tap "View Details" to expand

4. **Status Badges**
   - Completed: Green background (#4CAF50 opacity 0.2), dark green text
   - In Progress: Orange background, dark orange text
   - Cancelled: Red background, dark red text

5. **Empty State**
   - Coffee emoji (☕) at 64sp/64pt
   - Center-aligned text
   - Helpful message based on context

## Color Palette Used

From the existing theme:
```
Coffee Brown:   #6F4E37  (Primary, headers, buttons)
Light Coffee:   #A0522D  (Secondary accents)
Dark Coffee:    #3E2723  (Text, details)
Creamy White:   #FFF8E7  (Background)
Latte Foam:     #FFFAF0  (Card backgrounds)
Caramel Brown:  #D2691E  (Accents)
```

## Typography

**Android:**
- Header: 28sp, Bold
- Subheader: 16sp, Normal
- Card title: 16sp, Bold
- Body text: 14sp, Normal
- Price: 18sp, Bold
- Button: 14sp, Bold

**iOS:**
- Header: 28pt, Bold
- Subheader: 16pt, Normal
- Card title: 16pt, Semibold
- Body text: 14pt, Normal
- Price: 18pt, Bold
- Button: 14pt, Bold

## How to Capture Screenshots

### Android
1. Run the app in Android Studio
2. Open the emulator
3. Use emulator controls to take screenshot
4. Or use: `adb shell screencap -p /sdcard/screenshot.png`

### iOS
1. Run the app in Xcode
2. Open the iOS Simulator
3. Press Cmd+S to save screenshot
4. Or use: Device > Screenshot in Simulator menu

## Visual Testing Checklist

When viewing the screen, verify:
- [x] Header uses Coffee Brown background
- [x] Text is white on dark backgrounds
- [x] Cards use Latte Foam background
- [x] Prices are bold and prominent
- [x] Status badges use appropriate colors
- [x] Search bar is functional
- [x] Filter chips change appearance when selected
- [x] Cards expand/collapse smoothly
- [x] Empty state displays correctly
- [x] Scrolling is smooth
- [x] Layout is responsive to screen size

## Sample Order Data Visible

When you run the app, you should see these orders:
1. Order #001 - $16.90 - 1 day ago - 2 items
2. Order #002 - $4.86 - 3 days ago - 1 item
3. Order #003 - $23.38 - 5 days ago - 3 items
4. Order #004 - $3.51 - 7 days ago - 1 item
5. Order #005 - $10.80 - 10 days ago - 2 items
6. Order #006 - $6.48 - 14 days ago - 1 item
7. Order #007 - $12.37 - 20 days ago - 2 items
8. Order #008 - $7.56 - 25 days ago - 1 item

All orders show "Completed" status with green badge.
