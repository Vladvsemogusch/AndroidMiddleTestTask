package com.youarelaunched.challenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import org.junit.Rule
import org.junit.Test

class VendorsScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun when_vendorsEmpty_expect_NoResultVisible() {
        val uiState = VendorsScreenUiState(vendors = null, searchQuery = "")
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(
                    uiState = uiState,
                    onSearchQueryChange = {},
                    onSearchClick = {}
                )
            }
        }

        val emptyListTitle = composeTestRule.activity.getString(R.string.empty_list_title)
        val emptyListDescription =
            composeTestRule.activity.getString(R.string.empty_list_description)

        composeTestRule.onNodeWithText(emptyListTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(emptyListDescription).assertIsDisplayed()
    }

    @Test
    fun when_vendorsNotEmpty_expect_VendorItemVisible() {
        val vendor = Vendor(
            id = 0,
            companyName = "Cafe",
            coverPhoto = "https://test.com/test.png",
            area = "testArea",
            favorite = false,
            categories = null,
            tags = null
        )
        val uiState = VendorsScreenUiState(vendors = listOf(vendor), searchQuery = "")
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(
                    uiState = uiState,
                    onSearchQueryChange = {},
                    onSearchClick = {}
                )
            }
        }

        val vendorItemContentDescription =
            composeTestRule.activity.getString(R.string.cd_vendor_item)
        composeTestRule.onAllNodesWithContentDescription(vendorItemContentDescription)
            .apply {
                assertCountEquals(1)
                this[0].assertIsDisplayed()
            }
    }

}