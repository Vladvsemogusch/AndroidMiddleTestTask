package com.youarelaunched.challenge

import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.view.VendorsVM
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule

class VendorsVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @org.junit.Test
    fun getVendors_success_atLeast1Loads() = runTest {
        val vendor = Vendor(
            id = 0,
            companyName = "Cafe",
            coverPhoto = "https://test.com/test.png",
            area = "testArea",
            favorite = false,
            categories = null,
            tags = null
        )
        val vendorsRepository = mockk<VendorsRepository>()
        coEvery { vendorsRepository.getVendors(any()) } returns listOf(vendor)
        val vendorsVM = VendorsVM(vendorsRepository)

        vendorsVM.getVendors()
        advanceUntilIdle()

        Assert.assertFalse(vendorsVM.uiState.value.vendors.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @org.junit.Test
    fun getVendors_error_vendorListRemainsNull() = runTest {
        val vendorsRepository = mockk<VendorsRepository>()
        coEvery { vendorsRepository.getVendors(any()) } throws IllegalStateException()
        val vendorsVM = VendorsVM(vendorsRepository)

        vendorsVM.getVendors()
        advanceUntilIdle()

        Assert.assertTrue(vendorsVM.uiState.value.vendors == null)
    }
}