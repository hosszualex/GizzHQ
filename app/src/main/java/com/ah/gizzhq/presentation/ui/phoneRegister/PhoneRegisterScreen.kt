package com.ah.gizzhq.presentation.ui.phoneRegister

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.ah.gizzhq.domain.models.PhoneNumberCountryCode
import com.ah.gizzhq.presentation.theme.GizzHQTheme


@Composable
fun PhoneRegisterRoute(
    viewModel: PhoneRegisterViewModel = hiltViewModel(),
    onError: (Throwable) -> Unit,
    onLoading: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.error?.let { onError(it) }
    onLoading(uiState.isLoading)
    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.onEvent(PhoneNumberRegisterUiEvent.OnGetPhoneNumberCountryCodes)
    }
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

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var pickedCountryCode by remember {
        mutableStateOf(
            PhoneNumberCountryCode(
                countryCode = "",
                countryName = "",
                countryPhoneCode = "",
                phoneNumberHint = ""
            )
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
                .clip(RoundedCornerShape(30))
                .clipToBounds(),
            shape = RoundedCornerShape(30),
            value = phoneNumber,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {
                if (phoneNumber != it) {
                    phoneNumber = it
                }
            },
            singleLine = true,
            placeholder = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = pickedCountryCode.phoneNumberHint
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                autoCorrect = true,
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            leadingIcon = {
                CountryCodePicker(
                    pickedCountryCode,
                    countryList = uiState.phoneNumberCountryCodes.toList(),
                    onPickedCountry = { newPickedCountry ->
                        pickedCountryCode = newPickedCountry
                    })
            }
        )

        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(PhoneNumberRegisterUiEvent.SendPhoneNumberSecret(phoneNumber))
            },
            enabled = uiState.isNextButtonEnabled,
        ) {
            Text(text = "Register Account")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CountryCodePicker(
    defaultCountry: PhoneNumberCountryCode,
    countryList: List<PhoneNumberCountryCode>,
    onPickedCountry: (PhoneNumberCountryCode) -> Unit
) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                isSheetOpen = true
            },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = defaultCountry.countryPhoneCode,
                modifier = Modifier.padding(
                    start = 4.dp,
                    end = 0.dp
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "arrow down"
            )
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
        ) {
            LazyColumn {
                items(
                    countryList,
                    key = { it.countryCode }
                ) { countryItem ->
                    Row(
                        Modifier
                            .padding(
                                vertical = 0.dp,
                                horizontal = 0.dp
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(0.dp)
                            )
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                            .clickable {
                                isSheetOpen = false
                                onPickedCountry(countryItem)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = countryItem.countryName,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(200.dp)
                        )
                        Text(
                            text = countryItem.countryPhoneCode
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneRegisterPreview() {
    GizzHQTheme {
        PhoneRegisterScreen(
            uiState = PhoneNumberRegisterUiState(),
            onEvent = {},
        )
    }
}
