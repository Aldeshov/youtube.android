package com.aldeshov.youtube.service.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.aldeshov.youtube.service.database.models.LocalUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
    private lateinit var database: Database

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            Database::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertUserAndGetById() = runBlockingTest {
        // GIVEN - Insert a user.
        val user = LocalUser(id = 1, email = "test@mail.com", full_name = "User Test")
        database.userDao().insert(user)

        // WHEN - Get the user (id == 1) from the database.
        val loaded = database.userDao().getUser()

        // THEN - The loaded data contains the expected values.
        assertThat(loaded as LocalUser, notNullValue(LocalUser::class.java))
        assertThat(loaded.id, `is`(user.id))
        assertThat(loaded.full_name, `is`(user.full_name))
        assertThat(loaded.email, `is`(user.email))
    }
}