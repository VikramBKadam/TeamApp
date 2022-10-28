package com.example.teamapp.view

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.teamapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InviteMemberActivityTest{
private  lateinit var scenario: ActivityScenario<InviteMemberActivity>


@Before
fun setup() {
    scenario = ActivityScenario.launch(InviteMemberActivity::class.java)
    scenario.moveToState(Lifecycle.State.RESUMED)
}


    @Test
    fun testSpinnerAndShareButtonViewsAreDisplayed() {
        onView(ViewMatchers.withId(R.id.spinner))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.copy_link))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}