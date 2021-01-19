package cz.wayne.kmplayground.shared


import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    actual val dbLocationPath: String = "TODO"
}