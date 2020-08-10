package ch.zhaw.androidweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {




    // == fields ==
    private String newCity;




    // == main public methods ==

    /**
     * onCreateView()
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return  attacheToRoot: true/false
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout "R.layout.fragment_first" for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }


    /**
     * onViewCreated()
     * Handle manual city search by search query / EditText.
     * Handle Button for current location.
     * @param view  View
     * @param savedInstanceState    Bundle
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        Handle Button for current location
        */
        View geolocationButton = view.findViewById(R.id.geoloc_btn);

        geolocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // set city search flag = false
                MainActivity.setIsCitySearch(false);

                // navigation to second fragment
                navigateToSecondFragment();
            }
        });

         /*
         Handle manual city search by search query / EditText
         */
        final EditText editText = view.findViewById(R.id.citySearchQuery);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    // set city search flag = true
                    MainActivity.setIsCitySearch(true);

                    // handle entered String
                    newCity = editText.getText().toString();
                    Log.d("Debug", "newCity = " + newCity);
                    MainActivity.setCity(newCity);

                    // navigation to second fragment
                    navigateToSecondFragment();
                }
                return false;
            }
        });
    }


    /**
     * navigateToSecondFragment()
     * Handles the navigation to the second fragment.
     */
    private void navigateToSecondFragment() {
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }
}




