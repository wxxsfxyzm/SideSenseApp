package com.carlyu.sidesenseapp.permission

import com.carlyu.sidesenseapp.R

enum class PermissionGroup(
    val permissionGroupName: String,
    val permissionList: List<String>,
    val postDialogMessageId: Int
) {
    BLUETOOTH(
        "android.permission-group.NEARBY_DEVICES",
        listOf("android.permission.BLUETOOTH_CONNECT"),
        R.string.ss_strings_permission_rationale_location_txt
    );

    fun contains(permission: String): Boolean {
        return permissionList.contains(permission)
    }

    fun getGroupName(): String {
        return permissionGroupName
    }

    fun getPermissionGroupName(): String {
        return name
    }

    fun getPermissionList(): List<String> {
        return permissionList
    }

    fun getPostDialogMessageId(): Int {
        return postDialogMessageId
    }
}
