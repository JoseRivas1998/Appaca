package edu.csuci.appaca.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.utils.AssetsUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class IconBadgeFragment extends Fragment {

    private int iconId = -1;
    private String badgeText;
    private String iconPath;
    private View.OnClickListener onIconClick;

    public IconBadgeFragment() {
        // Required empty public constructor
    }

    public static IconBadgeFragment ofResource(int iconId, String badgeText) {
        IconBadgeFragment fragment = new IconBadgeFragment();
        fragment.iconId = iconId;
        fragment.badgeText = badgeText;
        fragment.onIconClick = null;
        return fragment;
    }

    public static IconBadgeFragment ofAsset(String iconPath, String badgeText) {
        IconBadgeFragment fragment = new IconBadgeFragment();
        fragment.iconPath = iconPath;
        fragment.badgeText = badgeText;
        fragment.onIconClick = null;
        return fragment;
    }

    public void setOnIconClick(View.OnClickListener onIconClick) {
        this.onIconClick = onIconClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_icon_badge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView icon = view.findViewById(R.id.icon_badge_icon);
        final TextView text = view.findViewById(R.id.icon_badge_text);

        text.setText(badgeText);
        if(iconPath != null) {
            icon.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), iconPath));
        } else {
            icon.setImageResource(iconId);
        }
        if(onIconClick != null) {
            icon.setOnClickListener(onIconClick);
        }
    }
}
