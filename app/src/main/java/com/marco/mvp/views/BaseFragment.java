package com.marco.mvp.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.marco.mvp.R;
import com.marco.mvp.views.login.LoginActivity;

public abstract class BaseFragment<T extends BaseContract.Presenter> extends Fragment implements BaseContract.View<T> {

    protected T presenter;


    @Override
    public Context getContext() {
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getContext();
        } else {
            return getActivity();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        presenter = getPresenter();
        presenter.viewCreated();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getViewResource(), container, false);
        initViews(rootView);
        presenter.viewsCreated();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        presenter.viewsDestroyed();

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.viewDestroyed();

        super.onDestroy();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.action_logout) {
            presenter.logoutButtonClicked();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_logout_black);
        builder.setTitle(R.string.logout_confirmation_title);
        builder.setMessage(R.string.logout_confirmation_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.logoutConfirmed();
            }
        });

        builder.setNegativeButton(R.string.no, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

}
