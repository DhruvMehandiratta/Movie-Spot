package com.androsol.moviespot.TVFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androsol.moviespot.Activities.SeasonActivity;
import com.androsol.moviespot.R;

/**
 * Created by Dhruv on 01-05-2017.
 */

public class TVSeasonFragment extends Fragment {

    Long tv_id;
    EditText seasonNo;
    Button seasonButton;
    Long season_number;
    Long noOfSeasons;
    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tv_season_fragment,container,false);
        Bundle b = getArguments();
        tv_id = b.getLong("TVId");
        noOfSeasons = b.getLong("NoOfSeasons");

        //TODO upper check for those whose no of episodes are not specified

        seasonNo = (EditText) v.findViewById(R.id.season_number_text);
        seasonButton = (Button) v.findViewById(R.id.season_button);
        seasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open new activity and pass it object seasonSearched
                if(seasonNo.getText().length() == 0){
                    Snackbar.make(getView(), "Please enter a season number!", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                try {
                    season_number = (long)Integer.parseInt(seasonNo.getText().toString());
                }catch(NumberFormatException e){
                    Snackbar.make(getView(), "Please enter an 'INTEGERAL' value", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if(season_number > noOfSeasons || season_number < 1){
                    Snackbar.make(getView(), "Please enter a valid season number that lies in the range of this show!", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                Intent i = new Intent(getActivity(), SeasonActivity.class);
                i.putExtra("SeasonNumber",season_number);
                i.putExtra("TV_ID",tv_id);
                startActivity(i);
            }
        });
        return v;
    }
}
