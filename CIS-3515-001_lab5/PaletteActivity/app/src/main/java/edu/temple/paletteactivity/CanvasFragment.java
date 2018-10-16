package edu.temple.paletteactivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CanvasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CanvasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanvasFragment extends Fragment {

    public static String COLOR_KEY = "color";
    int color;
    View view;

    public CanvasFragment() {
        // Required empty public constructor
    }//end CanvasFragment

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param color Parameter 1.
     * @return A new instance of fragment CanvasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CanvasFragment newInstance(int color) {
        CanvasFragment fragment = new CanvasFragment();
        Bundle args = new Bundle();
        args.putInt(CanvasFragment.COLOR_KEY, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.color = bundle.getInt(COLOR_KEY);

    }//end onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.canvas_fragment, container, false);
        view.setBackgroundColor(color);
        // Inflate the layout for this fragment
        return view;
    }//end onCreateView

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }//end onAttach



}//end CanvasFragment
