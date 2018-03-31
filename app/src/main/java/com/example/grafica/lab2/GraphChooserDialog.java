package com.example.grafica.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Sex_predator on 02.03.2016.
 */
public class GraphChooserDialog extends DialogFragment {

    public static final String EXTRA_GRAPH_CHOSEN = "graphChosen";

    private int mGraphChosen;

    public static GraphChooserDialog newInstance(int graphChosen) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_GRAPH_CHOSEN, graphChosen);

        GraphChooserDialog fragment = new GraphChooserDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mGraphChosen = getArguments().getInt(EXTRA_GRAPH_CHOSEN);

        String[] graphs = new String[] {GraphCreator.getGraph6Name(),
                                        GraphCreator.getGraph8Name(),
                                        GraphCreator.getGraph10Name(),
                                        GraphCreator.getGraph12Name()};

        return new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.choose_graph))
                .setSingleChoiceItems(graphs,
                                      getGraphPosition(mGraphChosen),
                                      new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              mGraphChosen = which * 2 + 6;
                                          }
                                      })
                .setPositiveButton(android.R.string.ok,
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           sendResult(Activity.RESULT_OK);
                                       }
                                   })
                .create();
    }

    private int getGraphPosition(int graphNumber) {
        return (graphNumber - 6) / 2;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_GRAPH_CHOSEN, mGraphChosen);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
