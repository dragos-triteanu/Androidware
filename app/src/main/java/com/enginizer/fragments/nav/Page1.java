package com.enginizer.fragments.nav;


import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enginizer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page1 extends Fragment {

    @BindView(R.id.btnCreateNotify)
    Button createNotificationButton;


    public Page1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_page1, container, false);
        ButterKnife.bind(this,inflatedView);

        this.createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationManager nManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
                NotificationCompat.Builder ncomp = new NotificationCompat.Builder(getActivity());
                ncomp.setContentTitle("My Notification");
                ncomp.setContentText("Notification Listener Service Example");
                ncomp.setTicker("Notification Listener Service Example");
                ncomp.setSmallIcon(R.drawable.callerq_icon);
                ncomp.setAutoCancel(true);
                nManager.notify((int)System.currentTimeMillis(),ncomp.build());
//
//                Intent i = new Intent("com.enginizer.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
//                i.putExtra("command","clearall");
//                getActivity().sendBroadcast(i);
            }
        });


        return inflatedView;
    }

}
