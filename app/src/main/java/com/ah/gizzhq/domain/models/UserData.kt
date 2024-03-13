package com.ah.gizzhq.domain.models

data class UserData(
    val email: String? = null,
    val displayName: String? = null,
    val userId: String? = null,
) {
    val isLoggedIn: Boolean
        get() = !email.isNullOrEmpty() && !displayName.isNullOrEmpty()

    val isRegistered: Boolean
        get() = !email.isNullOrEmpty() && displayName.isNullOrEmpty()
}
