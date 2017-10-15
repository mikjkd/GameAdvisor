import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miche.gameadvisorprova3.MainActivity;
import com.miche.gameadvisorprova3.R;

import static com.miche.gameadvisorprova3.R.id.AccediBtn;
import static com.miche.gameadvisorprova3.R.id.etEmail;
import static com.miche.gameadvisorprova3.R.id.etPsw;

/**
 * Created by Fabio on 15/10/2017.
 */

public class AlertDialogLogin extends AlertDialog.Builder {


    public AlertDialogLogin(@NonNull Context context) {
        super(context);
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        View mView = getLayoutInflater().inflate(R.layout.login_popup, null);
        EditText mEmail = mView.findViewById(etEmail);
        EditText mPassword = mView.findViewById(etPsw);
        Button mAccedi = mView.findViewById(AccediBtn);
    }
}
