package JdbcUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/*
* 1,抽取一个方法抽取注册驱动
* 2，写个方法获取连接
    不想传递参数，还想保证工具类通用性
        解决方法：配置文件
* 3，写一个方法释放资源
* */

public class JdbcUtils {

    private  static String url;
    private  static String username;
    private  static String password;
    private static String diver;
    /*
    * 文件的读取只要一次就行就可以拿到这些值，使用静态代码块
    * */
    static {
        //读取文件，获取该三值
        //1,创建Properties集合类
        Properties pro = new Properties();

        try {
            //获取文件地址  --有个方法获取scr下的路径ClassLoader
            ClassLoader classLoader = JdbcUtils.class.getClassLoader();
            URL res=classLoader.getResource("jdbc.properties");
            var path = res.getPath();
            System.out.println(path);
            pro.load(new FileReader(path));

            //获取数据
             url = pro.getProperty("url");
             username= pro.getProperty("user");
             password = pro.getProperty("password");
             diver=pro.getProperty("diver");
             //注册驱动
            try {
                Class.forName(diver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Connection getConnection() throws SQLException {

        //写个方法获取连接
            return DriverManager.getConnection(url,username,password);
    }

    public  static  void close(Statement stmt,Connection conn){
        if(stmt !=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public  static  void close(ResultSet rs,Statement stmt, Connection conn){
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt !=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
