package cdreyfus.xebia_henri_potier.catalogue

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import cdreyfus.xebia_henri_potier.loadCatalogue
import cdreyfus.xebia_henri_potier.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.junit.Before



@RunWith(AndroidJUnit4::class)
class CataloguePresenterTest {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(CatalogueActivity::class.java)

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Before
    @Throws(Exception::class)
    fun before() {
        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        val context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
    }


    @Test
    fun getCatalogue() {
        val presenter = CataloguePresenter(mActivityRule.activity, sharedPreferences)

//        presenter.getCatalogue()
        val bookList = loadCatalogue(sharedPreferences)
        assertEquals(bookList.size, 7)
    }

}