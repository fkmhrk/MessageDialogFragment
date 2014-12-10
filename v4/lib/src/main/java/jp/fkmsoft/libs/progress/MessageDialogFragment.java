package jp.fkmsoft.libs.progress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Dialog fragment for displaying Message
 */
public class MessageDialogFragment extends DialogFragment {
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "message";
    private static final String ARGS_EXTRA = "extra";

    public static final String EXTRA_DATA = "extra";

    public static MessageDialogFragment newInstance(Fragment target, int requestCode,
                                                     String title, String message, Bundle extra) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.setTargetFragment(target, requestCode);
        
        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        args.putBundle(ARGS_EXTRA, extra);
        fragment.setArguments(args);
        
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) { return null; }

        Bundle args = getArguments();
        String title = args.getString(ARGS_TITLE);
        String message = args.getString(ARGS_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submit(Activity.RESULT_OK);
            }
        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        submit(Activity.RESULT_CANCELED);
    }

    private void submit(int resultCode) {
        Fragment target = getTargetFragment();
        if (target == null) { return; }

        Bundle args = getArguments();
        Bundle extra = args.getBundle(ARGS_EXTRA);

        Intent data = new Intent();
        data.putExtra(EXTRA_DATA, extra);

        target.onActivityResult(getTargetRequestCode(), resultCode, data);
    }
}
