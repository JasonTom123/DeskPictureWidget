package breeze.desk.picmodel.widgets;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.appwidget.AppWidgetManager;
import android.widget.RemoteViews;
import breeze.desk.picmodel.R;
import android.content.ComponentName;
import brz.breeze.bitmap_utils.BBitmapUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import brz.breeze.tool_utils.Blog;
import java.io.File;
import breeze.desk.picmodel.fragment.AppPreference;

public class AppWidgetFirst extends AppWidgetProvider {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(AppPreference.FILE_NANE_WIDGET_FIRST.equalsIgnoreCase(intent.getAction())){
			update(context);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		update(context);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	private void update(Context context){
		String filepath = AppPreference.getPicFile(context,AppPreference.FILE_NANE_WIDGET_FIRST);
		if(new File(filepath).exists()){
			AppWidgetManager awm = AppWidgetManager.getInstance(context);
			RemoteViews rm = new RemoteViews(context.getPackageName(),R.layout.view_widget);
			ComponentName cn = new ComponentName(context,AppWidgetFirst.class);
			Bitmap bitmap = BitmapFactory.decodeFile(filepath);
			rm.setImageViewBitmap(R.id.view_widgetImageView,BBitmapUtils.getRoundedBitmap(bitmap,200));
			awm.updateAppWidget(cn,rm);
		}
	}
    
    /*
	*@author BREEZE
	*@date 2021-06-07 23:35:52
    */
    
}
