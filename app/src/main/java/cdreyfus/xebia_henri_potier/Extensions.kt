package cdreyfus.xebia_henri_potier

import android.view.View

internal fun View.toggleVisibility() {
    if (visibility == View.VISIBLE) {
        visibility = View.INVISIBLE
    } else {
        visibility = View.VISIBLE
    }
}