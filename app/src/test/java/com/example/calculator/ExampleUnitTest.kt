package com.example.calculator

import com.example.calculator.di.AppModule
import com.example.calculator.di.createViewModelFactory
import com.example.detail.DetailViewModel
import com.example.domain.AddDiaryUseCase
import com.example.domain.DiaryDto
import com.example.domain.GetDiaryUseCase
import com.example.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * DI 통합 테스트
 */
class ExampleUnitTest {

    private lateinit var appModule: AppModule

    @Before
    fun setup() {
        appModule = AppModule.getInstance()
    }

    @Test
    fun appModule_singleton_test() {
        val instance1 = AppModule.getInstance()
        val instance2 = AppModule.getInstance()

        assertSame("AppModule should be singleton", instance1, instance2)
    }

    @Test
    fun appModule_provides_useCases() {
        val addDiaryUseCase = appModule.addDiaryUseCase()
        val getDiaryUseCase = appModule.getDiaryListUseCase()

        assertNotNull("AddDiaryUseCase should not be null", addDiaryUseCase)
        assertNotNull("GetDiaryUseCase should not be null", getDiaryUseCase)
        assertTrue("AddDiaryUseCase should be correct type", addDiaryUseCase is AddDiaryUseCase)
        assertTrue("GetDiaryUseCase should be correct type", getDiaryUseCase is GetDiaryUseCase)
    }

    @Test
    fun appModule_provides_same_instance() {
        val useCase1 = appModule.addDiaryUseCase()
        val useCase2 = appModule.addDiaryUseCase()

        assertSame("Should provide same instance (lazy initialization)", useCase1, useCase2)
    }

    @Test
    fun viewModelFactory_creates_viewModel() {
        val addDiaryUseCase = appModule.addDiaryUseCase()

        val factory = createViewModelFactory {
            HomeViewModel(addDiaryUseCase)
        }

        val viewModel = factory.create(HomeViewModel::class.java)

        assertNotNull("ViewModel should not be null", viewModel)
        assertTrue("Should create HomeViewModel", viewModel is HomeViewModel)
    }

    @Test
    fun viewModelFactory_creates_different_instances() {
        val addDiaryUseCase = appModule.addDiaryUseCase()

        val factory = createViewModelFactory {
            HomeViewModel(addDiaryUseCase)
        }

        val viewModel1 = factory.create(HomeViewModel::class.java)
        val viewModel2 = factory.create(HomeViewModel::class.java)

        assertNotSame("Should create different ViewModel instances", viewModel1, viewModel2)
    }

    @Test
    fun integration_test_di_flow() {
        // AppModule에서 UseCase 가져오기
        val addDiaryUseCase = appModule.addDiaryUseCase()

        // ViewModelFactory로 ViewModel 생성
        val factory = createViewModelFactory {
            HomeViewModel(addDiaryUseCase)
        }
        val homeViewModel = factory.create(HomeViewModel::class.java)

        // DI가 제대로 동작하는지 확인
        assertNotNull("Full DI flow should work", homeViewModel)
        assertTrue(
            "Should create HomeViewModel with injected dependencies",
            homeViewModel is HomeViewModel
        )
    }


    @Test
    fun integration_test_di_flow_with_actual_behavior() = runTest {
        // Given: DI로 ViewModel 생성
        val addDiaryUseCase = appModule.addDiaryUseCase()
        val getDiaryUseCase = appModule.getDiaryListUseCase()

        val factory1 = createViewModelFactory { HomeViewModel(addDiaryUseCase) }
        val factory2 = createViewModelFactory { DetailViewModel(getDiaryUseCase) }

        val homeViewModel = factory1.create(HomeViewModel::class.java)
        val detailViewModel = factory2.create(DetailViewModel::class.java)

        // When: ViewModel의 실제 기능 사용
        homeViewModel.save("1", "테스트1")
        homeViewModel.save("2", "테스트2")

        val dateList = detailViewModel.dateList.first()
        println("dateList ${dateList}")

        assertEquals(
            "Should be Success when valid input",
            listOf(DiaryDto("1", "테스트1"), DiaryDto("2", "테스트2")),
            dateList
        )

    }
}