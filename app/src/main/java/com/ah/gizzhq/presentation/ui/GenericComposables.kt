package com.ah.gizzhq.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ah.gizzhq.R
import com.ah.gizzhq.xmaterialccp.component.MaterialCountryCodePicker
import com.ah.gizzhq.xmaterialccp.data.utils.checkPhoneNumber
import com.ah.gizzhq.xmaterialccp.data.utils.getDefaultLangCode
import com.ah.gizzhq.xmaterialccp.data.utils.getDefaultPhoneCode
import com.ah.gizzhq.xmaterialccp.data.ccpDefaultColors
import com.ah.gizzhq.xmaterialccp.data.utils.getLibCountries

@Composable
fun PasswordTextField(
    label: String,
    valuePassword: String,
    onValueChange: (String) -> Unit,
    isPasswordValid: Boolean,
) {
    var isVisible by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valuePassword,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = true,
        singleLine = true,
        label = { Text(text = label) },
        isError = !isPasswordValid,
        trailingIcon = {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(
                    painterResource(id = if (isVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                    contentDescription = "Visibility",
                )
            }
        },
    )
}

@Composable
fun EmailTextField(
    label: String,
    valueEmail: String,
    onValueChange: (String) -> Unit,
    isEmailValid: Boolean,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueEmail,
        onValueChange = onValueChange,
        enabled = true,
        singleLine = true,
        label = { Text(text = label) },
        isError = !isEmailValid,
    )
}

@Composable
fun PhoneNumberTextField(
    label: String,
    valuePhoneNumber: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valuePhoneNumber,
        onValueChange = onValueChange,
        enabled = true,
        singleLine = true,
        label = { Text(text = label) }
    )
}

@Composable
fun ErrorText(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Start,
        color = Color.Red,
    )
}

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .pointerInput(Unit) {},
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun SelectCountryWithCountryCode() {
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }

    MaterialCountryCodePicker(
        pickedCountry = {
            phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode
        },
        defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
        error = !isValidPhone,
        text = phoneNumber.value,
        onValueChange = { phoneNumber.value = it },
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
    )

    val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
    val checkPhoneNumber = checkPhoneNumber(
        phone = phoneNumber.value,
        fullPhoneNumber = fullPhoneNumber,
        countryCode = defaultLang
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            isValidPhone = checkPhoneNumber
        },
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        Text(text = "Phone Verify")
    }
}
