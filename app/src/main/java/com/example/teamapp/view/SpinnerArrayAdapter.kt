package com.example.teamapp.view

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.teamapp.utils.PermissionUtils
import com.example.teamapp.model.Team


class SpinnerArrayAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    values: List<CharSequence>,
    team: Team
) : ArrayAdapter<CharSequence>(
    context, layoutResource,
    values
) {
    private val currentTeam = team

    override fun isEnabled(position: Int): Boolean {
        if (currentTeam.members.total == currentTeam.plan.memberLimit) {
            if (getItem(position) == PermissionUtils.PLAYER ||
                getItem(position) == PermissionUtils.COACH ||
                getItem(position) == PermissionUtils.PLAYER_COACH
            ) {
                return false
            }
        }
        if (currentTeam.plan.supporterLimit != PermissionUtils.MINIMUM_SUPPORTERS_LIMIT && currentTeam.members.supporters >= currentTeam.plan.supporterLimit) {
            if (getItem(position) == PermissionUtils.SUPPORTER) {
                return false
            }
        }
        return true
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mView = super.getDropDownView(position, convertView, parent)
        val mTextView = mView as TextView
        if (currentTeam.plan.supporterLimit == PermissionUtils.MINIMUM_SUPPORTERS_LIMIT) {
            if (getItem(position) == PermissionUtils.SUPPORTER) {
                remove(PermissionUtils.SUPPORTER)
            }
        }
        if (currentTeam.members.total == currentTeam.plan.memberLimit) {
            if (getItem(position) == PermissionUtils.PLAYER ||
                getItem(position) == PermissionUtils.COACH ||
                getItem(position) == PermissionUtils.PLAYER_COACH
            ) {
                mTextView.setTextColor(Color.GRAY)
            } else {
                mTextView.setTextColor(Color.BLUE)
            }
        } else if (currentTeam.plan.supporterLimit != PermissionUtils.MINIMUM_SUPPORTERS_LIMIT &&
            currentTeam.members.supporters == currentTeam.plan.supporterLimit
        ) {
            if (getItem(position) == PermissionUtils.SUPPORTER) {
                mTextView.setTextColor(Color.GRAY)
            } else {
                mTextView.setTextColor(Color.BLUE)
            }
        } else {
            mTextView.setTextColor(Color.BLUE)
        }
        return mView;
    }

}