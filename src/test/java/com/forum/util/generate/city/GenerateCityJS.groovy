package com.forum.util.generate.city

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class GenerateCityJS {
    static int max = 0;
    static StringBuilder s1 = new StringBuilder("");
    static List<CityBean> list = new ArrayList<CityBean>();
    static Connection con = null;
    public static Connection getMySQLConnection() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/forum?verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf-8";
        String user = "root";
        String password = "root";
        Class.forName(driver);
        if(con == null)
            con = DriverManager.getConnection(url,user,password);
        return con;
    }
    public static void f(int node,int id) throws Exception{
        Connection con = getMySQLConnection();
        Statement stmt = null;
        ResultSet res = null;
        String sql = "SELECT * FROM forum.sys_areas where parent_id ='"+id+"'";
        stmt = con.createStatement();
        res = stmt.executeQuery(sql);
        String s ="";
        while(res.next()) {
            CityBean c = new CityBean();
            c.setId(res.getInt("id"));
            c.setMaxid(max++);
            c.setName(res.getString("name"));
            s += "[\""+c.getMaxid()+"\",\""+c.getName()+"\"],";
            list.add(c);
        }
        if(id == 100000) {
            CityBean c = new CityBean();
            c.setId(-1);
            c.setMaxid(max++);
            c.setName("海外");list.add(c);s += "[\""+c.getMaxid()+"\",\""+c.getName()+"\"],";}
        if(s.indexOf(",")!=-1)
            s = s.substring(0, s.lastIndexOf(","));
        s1.append("addr_arr["+node+"]=["+s+"],");
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            f(max++,100000);

            for(int index=0;index<list.size();index++) {
                f(list.get(index).getMaxid(),list.get(index).getId());
            }
            System.out.println(s1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
