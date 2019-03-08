package ch.supsi.dti.isin.meteoapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import ch.supsi.dti.isin.meteoapp.R;

public class PositionPickerDialog extends DialogFragment {

    TextInputEditText text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_position,null);

        text=v.findViewById(R.id.placeTextInput);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Input Place")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String placeName=(String)text.getText().toString();
                        //Log.d("PositionPickerDialog",placeName+"sono qui");
                        sendResultBack(Activity.RESULT_OK,placeName);
                    }
                })
                .create();
    }
    private void sendResultBack(int resultCode, String place) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("return_place", place);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
