package com.example.grafica.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sex_predator on 01.03.2016.
 */
public class MainFragment extends Fragment {

    public static final int REQUEST_GRAPH = 0;

    private static final String GRAPH_CHOSEN = "graphChosen";

    private GraphView mGraphView;

    private int mGraphChosen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState != null)
            mGraphChosen = savedInstanceState.getInt(GRAPH_CHOSEN);
        else
            mGraphChosen = 8;

        mGraphView = (GraphView) v.findViewById(R.id.graph);
        invalidateGraph();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(getGraphName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset:
                mGraphView.resetGraph();
                return true;
            case R.id.menu_choose_graph:
                FragmentManager fm = getFragmentManager();
                GraphChooserDialog chooser = GraphChooserDialog.newInstance(mGraphChosen);
                chooser.setTargetFragment(MainFragment.this, REQUEST_GRAPH);
                chooser.show(fm, "DialogGraph");
                return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_GRAPH:
                int graphChosen = data.getIntExtra(GraphChooserDialog.EXTRA_GRAPH_CHOSEN, -1);
                if (graphChosen == -1 || graphChosen == mGraphChosen)
                    return;
                mGraphChosen = graphChosen;
                invalidateGraph();
                getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(GRAPH_CHOSEN, mGraphChosen);
    }

    private void invalidateGraph() {
        GraphView.Graph graph = null;
        switch (mGraphChosen) {
            case 6:
                graph = GraphCreator.createGraph6();
                break;
            case 8:
                graph = GraphCreator.createGraph8();
                break;
            case 10:
                graph = GraphCreator.createGraph10();
                break;
            case 12:
                graph = GraphCreator.createGraph12();
        }

        if (graph != null)
            mGraphView.setGraph(graph);
    }

    private String getGraphName() {
        switch (mGraphChosen) {
            case 6:
                return GraphCreator.getGraph6Name();
            case 8:
                return GraphCreator.getGraph8Name();
            case 10:
                return GraphCreator.getGraph10Name();
            case 12:
                return GraphCreator.getGraph12Name();
        }

        return "null";
    }

}
