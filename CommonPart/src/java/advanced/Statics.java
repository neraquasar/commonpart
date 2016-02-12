package advanced;

import entities.UserData;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author neraquasar
 */
public class Statics {
    
    //разделители с примерами того, как будет выглядеть в БД
    public static final String delimiter_1 = "_!"; //2_3_10_123_11
    public static final String delimiter_2 = "_:"; //2_3_10:1_123:3_11
    public static final String delimiter_3 = "_,"; //2_3_10:1,3,8_123:3,5_11
    public static final String delimiter_4 = "_-"; //2_3_10:1,3-7-6,8-4_123:3-4,5_11
    public static final String delimiter_5 = "_%"; //2_3_10:1,3?1-7-6?2?1,8-4_123:3-4?9,5_11
    
    public static final long documentToUpload_MAX_FILE_SIZE = 3145728;
    
    public static String string(String key){
        return ResourceBundle.getBundle("/Strings").getString(key);
    }
    
    public static String cropLastDelimiter (String s, String delimiter){
        return (s!=null
                && s.length()>=delimiter.length()
                && s.substring(s.length()-delimiter.length()).equals(delimiter))
                ? s.substring(0, s.length()-delimiter.length()) : s;
    }
    
    public static String firstUpperCase(String word) {     //Сделать первую букву строки большой. NULL-безопсаный.
        if (word == null || word.equals("")) return word;
        else return (word.substring(0, 1).toUpperCase() + word.substring(1)).trim();
    }
    
    public static String dateToString (Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
        return format.format(date);
    }
    
    public static TreeSet<String> StringToTreeset(String s, char delimiter){
        TreeSet<String> set = new TreeSet<>();
        if (s!=null && s.length()>0)
                for (String s1 : s.split(""+delimiter))
                    set.add(s1);
        return set;
    }
    
    public static String TreesetToString(TreeSet<String> set, String delimiter){
        StringBuilder s = new StringBuilder();
        if (set!=null)
            for (String item : set)
            {s.append(item); s.append(delimiter);}
        return cropLastDelimiter(s.toString(), delimiter);
    }
    
    public static Boolean passwordValidation (UserData user, String password){
        return calculateHashString(password).equals(user.getPasshash());
    }
    
    public static String calculateHashString (String s){
        if (s == null || "".equals(s))
            return "";
        else {
            return MD5(s);
        }
    }
    
    static String MD5(String input) {
        String res = "";
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {}
        algorithm.reset();
        algorithm.update(input.getBytes());
        byte[] md5 = algorithm.digest();
        String tmp = "";
        for (int i = 0; i < md5.length; i++) {
            tmp = (Integer.toHexString(0xFF & md5[i]));
            if (tmp.length() == 1) {
                res += "0" + tmp;
            } else {
                res += tmp;
            }
        }
        return res;
    }
    
    public static Date timeCorrect(Date time){ // делает на час меньше, чтобы писать настоящее время в БД
        return time==null ? null : new Date(time.getTime()-1000*60*60);
    }
       
}
