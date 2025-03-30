package com.example.ecommerce_app.feature_account.myorder.viewmodel

import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_account.history.OrderStatus
import com.example.ecommerce_app.feature_account.history.viewmodel.OrdersScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class OrdersScreenViewModelTest {

    private lateinit var viewModel: OrdersScreenViewModel
    private lateinit var userPreferences: UserPreferences

    @Before
    fun setUp() {
        userPreferences = mock(UserPreferences::class.java)
        viewModel = OrdersScreenViewModel(userPreferences)
    }

    @Test
    fun `orders list is initialized correctly`() = runTest {
        val orders = viewModel.myOrderList.first()
        assertEquals(6, orders.size)

        assertEquals("ORD123", orders[0].id)
        assertEquals("March 20, 2025", orders[0].date)
        assertEquals("$50.00", orders[0].amount)
        assertEquals(OrderStatus.DELIVERED, orders[0].status)

        assertEquals("ORD125", orders[2].id)
        assertEquals(OrderStatus.COMPLETED, orders[2].status)
    }
}
