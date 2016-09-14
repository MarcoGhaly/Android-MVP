package com.marco.mvp.views.home;

import android.view.View;
import android.widget.TextView;

import com.marco.mvp.R;
import com.marco.mvp.model.beans.User;
import com.marco.mvp.views.BaseFragment;

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {

    private TextView textView_username;


    @Override
    public HomeContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new HomePresenter(this);
        }
        return presenter;
    }

    @Override
    public int getViewResource() {
        return R.layout.fragment_home;
    }


    @Override
    public void initViews(View rootView) {
        textView_username = (TextView) rootView.findViewById(R.id.textView_username);
    }


    @Override
    public void updateViews(User user) {
        textView_username.setText(user.getUsername());
    }


}
