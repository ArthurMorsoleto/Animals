package com.example.animals

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animals.di.AppModule
import com.example.animals.di.DaggerViewModelComponent
import com.example.animals.model.Animal
import com.example.animals.model.AnimalApiService
import com.example.animals.model.ApiKey
import com.example.animals.util.SharedPreferencesHelper
import com.example.animals.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalService: AnimalApiService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    private val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application, true)

    private val key = "Test Key"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun getApiKeySuccess() {
        Mockito.`when`(animalService.getApiKey()).thenReturn(null)

        val testSingle = Single.just(ApiKey("OK", key))
        Mockito.`when`(animalService.getApiKey()).thenReturn(testSingle)

        val animal = Animal("cow", null, null, null, null, null, null)
        val animalList = listOf(animal)
        val testAnimalSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testAnimalSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animalsList.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeyError(){
        Mockito.`when`(animalService.getApiKey()).thenReturn(null)

        val testSingle = Single.error<ApiKey>(Throwable())
        Mockito.`when`(animalService.getApiKey()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animalsList.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalSuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val animal = Animal("cow", null, null, null, null, null, null)
        val animalList = listOf(animal)
        val testSingle = Single.just(animalList)

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animalsList.value?.size)
    }

    @Test
    fun getAnimalsError() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("Ok", key))

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animalsList.value)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
    }

}