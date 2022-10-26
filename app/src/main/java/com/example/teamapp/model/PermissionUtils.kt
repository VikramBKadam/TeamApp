package com.example.teamapp.model

class PermissionUtils {
    companion object {
        const val PLAYER = "Player"
        const val COACH = "Coach"
        const val PLAYER_COACH = "Player Coach"
        const val SUPPORTER = "Supporter"
        const val MINIMUM_SUPPORTERS_LIMIT = 0.toLong()
        private const val MANAGER_ROLE = "manager"
        private const val EDITOR_ROLE = "editor"
        private const val MEMBER_ROLE = "member"
        private const val READONLY_ROLE = "readonly"

        fun getRole(selectedPermission: String): Role {
            return when (selectedPermission) {
                COACH -> Role(MANAGER_ROLE)
                PLAYER_COACH -> Role(EDITOR_ROLE)
                PLAYER -> Role(MEMBER_ROLE)
                else -> {
                    Role(READONLY_ROLE)
                }
            }
        }
    }
}
