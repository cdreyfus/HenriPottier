package cdreyfus.xebia_henri_potier

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import android.widget.NumberPicker
import org.hamcrest.Matcher


internal fun setBookInBasket_fromBookActivity(quantity: Int) {
    onView(ViewMatchers.withId(R.id.activity_book_add_to_basket)).perform(ViewActions.click())

    if(quantity > 1) {
        onView(ViewMatchers.withId(R.id.activity_book_edit_quantity_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.numberPicker)).perform(selectNumberPickerValue(quantity))
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
    }
}

internal fun selectNumberPickerValue(value: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "Set the value of a NumberPicker"
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(NumberPicker::class.java)
        }


        override fun perform(uiController: UiController, view: View) {
            val np = view as NumberPicker
            val method = np.javaClass.getDeclaredMethod("setValueInternal", Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(np, value, true)
        }
    }
}

//fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
//    checkNotNull(itemMatcher)
//    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
//        override fun describeTo(description: Description) {
//            description.appendText("has item at position $position: ")
//            itemMatcher.describeTo(description)
//        }
//
//        override fun matchesSafely(view: RecyclerView): Boolean {
//            val viewHolder = view.findViewHolderForAdapterPosition(position)
//                    ?: // has no item on such position
//                    return false
//            return itemMatcher.matches(viewHolder.itemView)
//        }
//    }
//}

// Convenience helper
fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewId)
}
