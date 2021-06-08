package breeze.desk.picmodel;
 
import android.app.Activity;
import android.os.Bundle;
import breeze.desk.picmodel.fragment.AppPreference;
import brz.breeze.service_utils.BExceptionCatcher;

public class MainActivity extends Activity { 
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(BExceptionCatcher.getInstance(this));
        setContentView(R.layout.activity_main);
		getFragmentManager().beginTransaction().replace(R.id.activity_mainfragment,
		new AppPreference());
    }
	
} 
