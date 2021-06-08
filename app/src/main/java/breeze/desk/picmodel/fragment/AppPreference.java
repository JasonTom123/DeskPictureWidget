package breeze.desk.picmodel.fragment;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import breeze.desk.picmodel.R;
import brz.breeze.file_utils.BFileUtils;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.text.SimpleDateFormat;
import brz.breeze.app_utils.BToast;
import breeze.desk.picmodel.widgets.AppWidgetFirst;
import android.content.Context;

public class AppPreference extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {

	@Override
	public boolean onPreferenceChange(Preference p1, Object p2) {
		return false;
	}

	@Override
	public boolean onPreferenceClick(Preference p1) {
		if("preference_first_widget".equals(p1.getKey())){
			choose();
			widget_id = 1;
		}else if("preference_second_widget".equals(p1.getKey())){
			choose();
			widget_id = 2;
		}else if("preference_third_widget".equals(p1.getKey())){
			choose();
			widget_id = 3;
		}
		return false;
	}
	
	private void choose(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent,100);
	}

	private String save_file;
	private int widget_id;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == BFileUtils.CHOOSE_FILE_REQUEST && resultCode == Activity.RESULT_OK){
			initSaveFile();
			Uri uri = Uri.fromFile(new File(save_file));
			UCrop.of(data.getData(),uri)
			.withAspectRatio(getAspect()[0],getAspect()[1])
			.start(getActivity(),this);
		}else if(requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK){
			saveData();
			updateAppwidget();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private int[] getAspect(){
		int[] ints = new int[2];
		switch(widget_id){
			case 1:
				ints[0]=1;
				ints[1]=1;
				break;
			case 2:
			case 3:
				ints[0]=16;
				ints[1]=9;
				break;
		}
		return ints;
	}
	
	private void initSaveFile(){
		switch(widget_id){
			case 1:
				save_file = getPicFile(getActivity(),FILE_NANE_WIDGET_FIRST);
				break;
			case 2:
				save_file = getPicFile(getActivity(),FILE_NANE_WIDGET_SENCOND);
				break;
			case 3:
				save_file = getPicFile(getActivity(),FILE_NANE_WIDGET_THIRD);
				break;
		}
	}
	
	private void saveData(){
		switch(widget_id){
			case 1:
				widget_first.setSummary(save_file);
				PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("preference_first_widget",save_file).apply();
				break;
			case 2:
				widget_second.setSummary(save_file);
				PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("preference_second_widget",save_file).apply();
				break;
			case 3:
				widget_third.setSummary(save_file);
				PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("preference_third_widget",save_file).apply();
				break;
		}
	}
	
	private void toast(String message){
		BToast.toast(getActivity(),message,0).show();
	}

	private Preference widget_first,widget_second,widget_third;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_view);
		init();
		initdata();
	}
	
	private void init(){
		updateAppwidget();
		widget_first = findPreference("preference_first_widget");
		widget_first.setOnPreferenceClickListener(this);
		widget_second = findPreference("preference_second_widget");
		widget_second.setOnPreferenceClickListener(this);
		widget_third = findPreference("preference_third_widget");
		widget_third.setOnPreferenceClickListener(this);
	}
	
	private void initdata(){
		widget_first.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("preference_first_widget","未定义"));
		widget_second.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("preference_second_widget","未定义"));
		widget_third.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("preference_third_widget","未定义"));
		
	}
	
	private void updateAppwidget(){
		Intent intent = new Intent(UPDATE_WIDGET);
		getActivity().sendBroadcast(intent);
	}
	
	public static String getPicFile(Context context,String filename){
		return context.getExternalFilesDir("images")+"/"+filename;
	}
    
	public static final String UPDATE_WIDGET = "breeze.desk.picmodel.fragment.UPDATE_APPWIDGET";
	
	public static final String FILE_NANE_WIDGET_FIRST = "widget_first.png";
	
	public static final String FILE_NANE_WIDGET_SENCOND = "widget_second.png";
	
	public static final String FILE_NANE_WIDGET_THIRD = "widget_third.png";
	
    /*
	*@author BREEZE
	*@date 2021-06-07 23:25:29
    */

    
}
