package org.fitnessapp;

//import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.fitnessapp.ui.main_activity.MainActivity;
import org.fitnessapp.ui.register.RegisterActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    private String username = "erald_haka";
    private String password = "!@E4S6";

    @Rule
    public IntentsTestRule<RegisterActivity> testRule = new IntentsTestRule<>(RegisterActivity.class);

    @Test
    public void registerUser(){

        onView(withId(R.id.edit_text_username)).perform(typeText(username));
        onView(withId(R.id.edit_text_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.button_register)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

}