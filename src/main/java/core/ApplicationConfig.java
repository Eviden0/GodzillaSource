package core;

import core.ui.component.dialog.GOptionPane;
import core.ui.component.dialog.ImageShowDialog;
import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import util.Log;
import util.functions;

public class ApplicationConfig {
   private static final String GITEE_CONFIG_URL = "https://gitee.com/beichendram/Godzilla/raw/master/%s";
   private static final String GIT_CONFIG_URL = "https://raw.githubusercontent.com/BeichenDream/Godzilla/master/%s";
   private static String ACCESS_URL = "https://gitee.com/beichendram/Godzilla/raw/master/%s";
   public static final String GIT = "https://github.com/BeichenDream/Godzilla";
   private static final HashMap<String, String> headers = new HashMap();

   public static void invoke() {
      if (functions.getCurrentJarFile() != null) {
         HashMap<String, String> configMap = null;

         try {
            configMap = getAppConfig(String.format("https://gitee.com/beichendram/Godzilla/raw/master/%s", "application.config"));
            ACCESS_URL = "https://gitee.com/beichendram/Godzilla/raw/master/%s";
         } catch (Exception var12) {
            try {
               configMap = getAppConfig(String.format("https://raw.githubusercontent.com/BeichenDream/Godzilla/master/%s", "application.config"));
               ACCESS_URL = "https://raw.githubusercontent.com/BeichenDream/Godzilla/master/%s";
            } catch (Exception var11) {
               Log.error("Network connection failure");
            }
         }

         String showGroupTitle;
         String tipString;
         if (configMap != null && configMap.size() > 0) {
            String version = (String)configMap.get("currentVersion");
            boolean isShowGroup = Boolean.valueOf((String)configMap.get("isShowGroup"));
            String wxGroupImageUrl = (String)configMap.get("wxGroupImageUrl");
            showGroupTitle = (String)configMap.get("showGroupTitle");
            tipString = (String)configMap.get("gitUrl");
            boolean isShowAppTip = Boolean.valueOf((String)configMap.get("isShowAppTip"));
            String appTip = (String)configMap.get("appTip");
            if (version != null && wxGroupImageUrl != null && appTip != null && tipString != null) {
               if (functions.stringToint(version.replace(".", "")) > functions.stringToint("4.01".replace(".", ""))) {
                  GOptionPane.showMessageDialog((Component)null, EasyI18N.getI18nString("新版本已经发布\n当前版本:%s\n最新版本:%s", "4.01", version), "message", 2);
                  functions.openBrowseUrl(tipString);
               }

               if (isShowAppTip) {
                  JOptionPane.showMessageDialog((Component)null, appTip, "message", 1);
               }

               if (isShowGroup) {
                  try {
                     ImageIcon imageIcon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(functions.httpReqest(wxGroupImageUrl, "GET", headers, (byte[])null))));
                     ImageShowDialog.showImageDiaolog(imageIcon, showGroupTitle);
                  } catch (IOException var9) {
                     Log.error((Throwable)var9);
                     Log.error("showGroup fail!");
                  }
               }
            }
         }

      }
   }

   private static HashMap<String, String> getAppConfig(String configUrl) throws Exception {
      byte[] result = functions.httpReqest(configUrl, "GET", headers, (byte[])null);
      if (result == null) {
         throw new Exception("readApplication Fail!");
      } else {
         String configString;
         try {
            configString = new String(result, "utf-8");
         } catch (UnsupportedEncodingException var10) {
            configString = new String(result);
         }

         HashMap<String, String> hashMap = new HashMap();
         String[] lines = configString.split("\n");
         String[] var5 = lines;
         int var6 = lines.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            int index = line.indexOf(58);
            if (index != -1) {
               hashMap.put(line.substring(0, index).trim(), line.substring(index + 1).trim());
            }
         }

         return hashMap;
      }
   }

   static {
      headers.put("Accept", "*/*");
      headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
   }
}
