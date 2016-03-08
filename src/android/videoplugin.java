package tw.com.bais.videoview2;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.content.Intent;

/**
 * This class echoes a string called from JavaScript.
 */
public class videoplugin extends CordovaPlugin {
    private CallbackContext callbackContext = null;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("play")) {
            this.callbackContext = callbackContext;
            CordovaResourceApi resourceApi = webView.getResourceApi();
            String target = args.getString(0);
            
            //final String url= args.getString(0);  
            final boolean showAds= false; 
            final boolean isLive=  false;
            final String adServer = "";
            
            final JSONObject options = args.getJSONObject(1);
        
            String fileUriStr;
            try {
                Uri targetUri = resourceApi.remapUri(Uri.parse(target));
                fileUriStr = targetUri.toString();
            } catch (IllegalArgumentException e) {
                fileUriStr = target;
            }
            final String path = stripFileProtocol(fileUriStr);
            
            cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context = cordova.getActivity().getApplicationContext();
                        Intent intent = new Intent(context,play.class);
                        intent.putExtra("url", path);
                        intent.putExtra("showAds", showAds);
                        intent.putExtra("isLive", isLive);
                        intent.putExtra("adServer", adServer);
                        cordova.getActivity().startActivity(intent);
                    }
                });
                
                return true;
        }
        return false;
    }
    
    public static String stripFileProtocol(String uriString) {
        if (uriString.startsWith("file://")) {
            return Uri.parse(uriString).getPath();
        }
        return uriString;
    }
 
}
