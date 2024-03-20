package com.ah.gizzhq.presentation.ui.profile.phoneRegister

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ah.gizzhq.presentation.ui.ProgressIndicator
import com.ah.gizzhq.presentation.ui.SelectCountryWithCountryCode
import com.ah.gizzhq.xmaterialccp.component.MaterialCountryCodePicker
import com.ah.gizzhq.xmaterialccp.data.ccpDefaultColors
import com.ah.gizzhq.xmaterialccp.data.utils.getDefaultLangCode
import com.ah.gizzhq.xmaterialccp.data.utils.getDefaultPhoneCode
import com.ah.gizzhq.xmaterialccp.data.utils.getLibCountries

@Composable
fun PhoneRegisterRoute(viewModel: PhoneRegisterViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PhoneRegisterScreen(
        uiState,
        viewModel::onEvent,
    )
}

@Composable
fun PhoneRegisterScreen(
    uiState: PhoneNumberRegisterUiState,
    onEvent: (PhoneNumberRegisterUiEvent) -> Unit,
) {

    val context = LocalContext.current

    var phoneNumber by remember {
        mutableStateOf(
            "+40752254082"
        )
    }

    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }


    val keyboardController = LocalSoftwareKeyboardController.current
    // call the viewModel variable and call the next screen
    // should be in UI state and call the on navigate to profile

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        MaterialCountryCodePicker(
            text = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            pickedCountry = {
                phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode
            },
            colors = ccpDefaultColors(
                primaryColor = MaterialTheme.colorScheme.primary,
                errorColor = MaterialTheme.colorScheme.error,
                backgroundColor = MaterialTheme.colorScheme.background,
                surfaceColor = MaterialTheme.colorScheme.surface,
                outlineColor = MaterialTheme.colorScheme.outline,
                disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
                unfocusedOutlineColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                cursorColor = MaterialTheme.colorScheme.primary,
                topAppBarColor = MaterialTheme.colorScheme.surface,
                countryItemBgColor = MaterialTheme.colorScheme.surface,
                searchFieldBgColor = MaterialTheme.colorScheme.surface,
                dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                dropDownIconTint = MaterialTheme.colorScheme.onBackground.copy(0.7f)

            )
            )

/*        MaterialCountryCodePicker(
            pickedCountry = {
                phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode
            },
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            error = !uiState.isPhoneNumberValid,
            text = phoneNumber,
            onValueChange = { phoneNumber = it },
            searchFieldPlaceHolderTextStyle = MaterialTheme.typography.bodyMedium,
            searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
            phonenumbertextstyle = MaterialTheme.typography.bodyMedium,
            countrytextstyle = MaterialTheme.typography.bodyMedium,
            countrycodetextstyle = MaterialTheme.typography.bodyMedium,
            showErrorText = true,
            showCountryCodeInDIalog = true,
            showDropDownAfterFlag = true,
            textFieldShapeCornerRadiusInPercentage = 40,
            searchFieldShapeCornerRadiusInPercentage = 40,
            appbartitleStyle = MaterialTheme.typography.titleLarge,
            countryItemBgShape = RoundedCornerShape(5.dp),
            showCountryCode = true,
            flagShape = RoundedCornerShape(10f),
            isEnabled = true,
            showErrorIcon = false,
            colors = ccpDefaultColors(
                primaryColor = MaterialTheme.colorScheme.primary,
                errorColor = MaterialTheme.colorScheme.error,
                backgroundColor = MaterialTheme.colorScheme.background,
                surfaceColor = MaterialTheme.colorScheme.surface,
                outlineColor = MaterialTheme.colorScheme.outline,
                disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
                unfocusedOutlineColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                cursorColor = MaterialTheme.colorScheme.primary,
                topAppBarColor = MaterialTheme.colorScheme.surface,
                countryItemBgColor = MaterialTheme.colorScheme.surface,
                searchFieldBgColor = MaterialTheme.colorScheme.surface,
                dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                dropDownIconTint = MaterialTheme.colorScheme.onBackground.copy(0.7f)

            ),
            errorTextStyle = MaterialTheme.typography.bodySmall,
            errorModifier = Modifier.padding(top = 3.dp, start = 10.dp)
        )*/

        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(PhoneNumberRegisterUiEvent.SendPhoneNumberSecret(phoneNumber))
            },
            enabled = uiState.isRegisterButtonEnabled,
        ) {
            Text(text = "Register Account")
        }
    }

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        ProgressIndicator()
    }
}
