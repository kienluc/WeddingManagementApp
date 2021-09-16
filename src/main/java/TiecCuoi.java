
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class TiecCuoi {
    private String tenTiec;
    private String tenSanh;
    private double giaThueSanh;
    private String thoiDiemThue; 
    
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Kiểm tra ngày nhập vào là ngày mới
     * @param time: Ngày cần kiểm tra theo định dạng dd/MM/yyyy
     * @return true: ngày mới/ false: ngày cũ
     * @throws ParseException 
     */
    public boolean checkDayAfter(String time) throws ParseException{
        long millis = System.currentTimeMillis();
        Date now = new Date(millis);
        Date a = f.parse(time);
        return a.after(now);
    }
    /**
     * Kiểm tra ngày nhập vào là thứ mấy
     * @param time: Ngày cần kiểm tra theo định dạng dd/MM/yyyy
     * @return Thứ trong tuần
     * @throws ParseException 
     */
    public String checkDay(String time) throws ParseException{        
        Date temp = f.parse(time);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String result = "";
          switch(dayOfWeek)
        {
            case 1:
            {
                result += "Sun";
                break;
            }
            case 2:
            {
                result += "Mon";
                break;
            }
            case 3:
            {
                result += "Tue";
                break;
            }
            case 4:
            {
                result += "Wed";
                break;
            }
            case 5:
            {
                result += "Thur";
                break;
            }
            case 6:
            {
                result += "Fri";
                break;
            }
            case 7:
            {
                result += "Sat";
                break;
            }
                                                   
        }
          return result;
    }
    /**
     * Trả về giá trị tháng của 1 ngày
     * @param time: Ngày theo định dạng dd/MM/yyyy
     * @return Tháng trong năm
     * @throws ParseException 
     */
    public int checkMonth(String time) throws ParseException
    {
        Date temp = f.parse(time);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        int month = c.get(Calendar.MONTH);
        return month;
    }
    /**
     * 
     * @param time: chuỗi thời gian dd/MM/yyyy
     * @return năm của thời gian vừa nhập
     * @throws ParseException 
     */
    public int checkYear(String time) throws ParseException{
        Date temp = f.parse(time);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        int year = c.get(Calendar.YEAR);
        return year;
    }
    /**
     * Đặt tiệc: Sảnh cưới - Menu - Dịch vụ đính kèm
     * @param scan
     * @param a: Sảnh cưới
     * @throws SQLException
     * @throws ParseException 
     */
    public void order(Scanner scan,SanhCuoi a) throws SQLException, ParseException{
        double getGiaSanhTuongUng = 0;
        scan.nextLine();       
        System.out.print("Nhap ten tiec: ");
        this.setTenTiec(scan.nextLine());
        a.showSanh();
        System.out.print("Chon ten sanh: ");
        this.setTenSanh(scan.nextLine());
        System.out.print("Thoi diem thue: ");
        this.thoiDiemThue = scan.nextLine();
        if(checkDayAfter(this.thoiDiemThue) == true)
        {
            int rand = (int)(Math.random()*999) + 3;
            String sub = "Menu " + String.valueOf(rand);
            String sub2 = "Service " + String.valueOf(rand);
            try (Connection con = ConnectDB.getSQLServerConnection()) {      
                String data = "SELECT *FROM SanhCuoi WHERE tenSanh=?";
                CallableStatement st1 = con.prepareCall(data);
                st1.setString(1,this.tenSanh);
                ResultSet rs = st1.executeQuery();
                while(rs.next())           
                    getGiaSanhTuongUng += rs.getDouble("giaThueSanh");

                //
                String sql = "INSERT INTO TiecCuoi (name,room,cost,date,menu,service)"
                        + " VALUES(?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1,this.tenTiec);
                st.setString(2,this.tenSanh);
                if(checkDay(this.thoiDiemThue).equals("Sat") || checkDay(this.thoiDiemThue).equals("Sun"))
                    st.setDouble(3,getGiaSanhTuongUng + 500000 );
                else
                    st.setDouble(3, getGiaSanhTuongUng);                                                          
                st.setString(4,checkDay(this.thoiDiemThue) + " " + this.thoiDiemThue);
                
                st.setString(5, sub);
                st.setString(6, sub2);
                st.executeUpdate();
                //
                String sql2 = "UPDATE SanhCuoi SET State=? WHERE tenSanh=?";
                PreparedStatement st2 = con.prepareStatement(sql2);
                st2.setString(1,"Checked");
                st2.setString(2,this.tenSanh);
                st2.executeUpdate();
                con.close();
                System.out.println("\n\t DAT SANH THANH CONG !!!");
            }
            // ORDER MENU
            ThucAn ta = new ThucAn();
            ThucUong tu = new ThucUong();
            ta.show();
            tu.show();
            System.out.println("\n\t\tChon menu (1 khai vi - 2 chinh - 1 trang mieng - 1 do uong: ");
            System.out.print("Ten mon khai vi: ");
            String kv = scan.nextLine();
            System.out.print("Ten mon chinh 1: ");
            String c1 = scan.nextLine();
            System.out.print("Ten mon chinh 2: ");
            String c2 = scan.nextLine();
            System.out.print("Ten mon trang mieng: ");
            String tm = scan.nextLine();
            System.out.print("Ten thuc uong: ");
            String drink = scan.nextLine();
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "INSERT INTO Menu (Menu,MonKhaiVi,MonChinh1,MonChinh2,MonTrangMieng,DoUong) "
                        + "VALUES(?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, sub);
                st.setString(2, kv);
                st.setString(3, c1);
                st.setString(4, c2);
                st.setString(5, tm);
                st.setString(6, drink);
                st.executeUpdate();
                con.close();                
            }
            //ORDER SERVICE
            DichVu pt = new DichVu();
            DichVu ka = new Karaoke();
            DichVu cs = new CaSi();
            pt.showDV();
            ka.showDV();
            cs.showDV();
            System.out.print("\n\t\tSo luong dich vu (toi da 3): ");
            int choice = scan.nextInt();      
            scan.nextLine();
            Connection con = ConnectDB.getSQLServerConnection();
            switch(choice)
            {
                case 1:
                {
                    System.out.print("Nhap ten dich vu: ");
                    String s = scan.nextLine();
                    String sql = "INSERT INTO Service (Combo,service1) "
                            + "VALUES(?,?)";
                    PreparedStatement st = con.prepareStatement(sql);
                    st.setString(1, sub2);
                    st.setString(2,s);
                    st.executeUpdate();
                    con.close();
                    break;
                }
                case 2:
                {
                    System.out.print("Nhap ten dich vu thu 1: ");
                    String s = scan.nextLine();
                    System.out.print("Nhap ten dich vu thu 2: ");
                    String s1 = scan.nextLine();
                    String sql = "INSERT INTO Service (Combo,service1,service2) "
                            + "VALUES(?,?,?)";
                    PreparedStatement st = con.prepareStatement(sql);
                    st.setString(1,sub2);
                    st.setString(2,s);
                    st.setString(3,s1);
                    st.executeUpdate();
                    con.close();
                    break;
                }
                case 3:
                {
                    System.out.print("Nhap ten dich vu thu 1: ");
                    String s = scan.nextLine();
                    System.out.print("Nhap ten dich vu thu 2: ");
                    String s1 = scan.nextLine();
                    System.out.print("Nhap ten dich vu thu 3: ");
                    String s2 = scan.nextLine();
                    String sql = "INSERT INTO Service (Combo,service1,service2,service3) "
                            + "VALUES(?,?,?,?)";
                    PreparedStatement st = con.prepareStatement(sql);
                    st.setString(1,sub2);
                    st.setString(2,s);
                    st.setString(3,s1);
                    st.setString(4,s2);
                    st.executeUpdate();
                    con.close();
                    break;
                }
                
            } 
            System.out.println("\n\t DAT TIEC THANH CONG !!!");
        }
        else
            System.out.println("\n\tDAT TIEC KHONG HOP LE");
            
    }
    
    /**
     * Hiển thị danh sách tiệc cưới đã đặt
     * @throws SQLException 
     */
    public void show() throws SQLException{
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM TiecCuoi";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                ++dem;
                if(dem == 1)
                    System.out.println("\n\tDanh sach tiec da dat: ");
                System.out.print("\n\nTen tiec: " + rs.getString("name"));
                System.out.print("\nTen sanh: " + rs.getString("room"));
                System.out.printf("\nGia thue sanh (T7-CN + 500k): %,.2f VND",rs.getDouble("cost"));
                System.out.print("\nNgay dat: " + rs.getString("date") + "\n");
                System.out.print("Menu: " + rs.getString("menu") + "\n");
                System.out.print("Service: " + rs.getString("service") + "\n");
            }
        }
        if(dem == 0)
            System.out.println("\n\t KHONG CO TIEC NAO DUOC DAT");
    }
    
    /**
     * Thanh toán 1 tiệc
     * @param scan
     * @throws SQLException 
     */
    public void thanhToan(Scanner scan) throws SQLException{
        String menuName = "";
        String serviceName = "";
        double menuCost = 0;
        double roomCost = 0;
        double serviceCost = 0;
        String date = "";
        scan.nextLine();
        System.out.print("\nNhap ten tiec muon thanh toan: ");
        String name = scan.nextLine();
        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM TiecCuoi WHERE name=?";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1,name);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                menuName += rs.getString("menu");
                roomCost += rs.getDouble("cost");
                serviceName += rs.getString("service");
                date += rs.getString("date");
            }
            
            
            //////////////////////////MENU
            String sql2 = "SELECT *FROM Menu WHERE Menu=?";
            CallableStatement cstm2 = con.prepareCall(sql2);
            cstm2.setString(1, menuName);
            ResultSet rs2 = cstm2.executeQuery();
            while(rs2.next())
            {
                String kv = rs2.getString("MonKhaiVi");
                String c1 = rs2.getString("MonChinh1");
                String c2 = rs2.getString("MonChinh2");
                String tm = rs2.getString("MonTrangMieng");
                String drink = rs2.getString("DoUong");
                
                //Gia mon khai vi
                String sql21 = "SELECT *FROM ThucAn WHERE TenMon=?";
                CallableStatement cstm21 = con.prepareCall(sql21);
                cstm21.setString(1, kv);
                ResultSet rs21 = cstm21.executeQuery();
                while(rs21.next())
                {
                    menuCost += rs21.getDouble("Gia");
                    
                }
                
                //Gia mon chinh 1
                String sql22 = "SELECT *FROM ThucAn WHERE TenMon=?";
                CallableStatement cstm22 = con.prepareCall(sql22);
                cstm22.setString(1, c1);
                ResultSet rs22 = cstm22.executeQuery();
                while(rs22.next())
                {
                    menuCost += rs22.getDouble("Gia");
                }
                
                //Gia mon chinh 2
                String sql23 = "SELECT *FROM ThucAn WHERE TenMon=?";
                CallableStatement cstm23 = con.prepareCall(sql23);
                cstm23.setString(1, c2);
                ResultSet rs23 = cstm23.executeQuery();
                while(rs23.next())
                {
                    menuCost += rs23.getDouble("Gia");
                }
                
                //Gia mon trang mieng
                String sql24 = "SELECT *FROM ThucAn WHERE TenMon=?";
                CallableStatement cstm24 = con.prepareCall(sql24);
                cstm24.setString(1, tm);
                ResultSet rs24 = cstm24.executeQuery();
                while(rs24.next())
                {
                    menuCost += rs24.getDouble("Gia");
                }
                
                //Gia thuc uong
                String sql25 = "SELECT *FROM ThucUong WHERE TenThucUong=?";
                CallableStatement cstm25 = con.prepareCall(sql25);
                cstm25.setString(1, drink);
                ResultSet rs25 = cstm25.executeQuery();
                while(rs25.next())
                {
                    menuCost += rs25.getDouble("Gia");
                }
            }
            
            
            /////////////////////SERVICE
            String serviceSQL = "SELECT *FROM Service WHERE combo=?";
            CallableStatement serviceCall = con.prepareCall(serviceSQL);
            serviceCall.setString(1, serviceName);
            ResultSet serviceRS = serviceCall.executeQuery();
            while(serviceRS.next())
            {
               String sv1 = serviceRS.getString("service1");
               String sv2 = serviceRS.getString("service2");
               String sv3 = serviceRS.getString("service3");
               
               //SERVICE 1
               {
               String sqlSV1 = "SELECT *FROM DVPhoThong WHERE tenDV=?";
               CallableStatement svCall1 = con.prepareCall(sqlSV1);
               svCall1.setString(1, sv1);
               ResultSet svRS1 = svCall1.executeQuery();
               while(svRS1.next())
               {
                   serviceCost += svRS1.getDouble("giaDV");

               }
               
               String sqlSV11 = "SELECT *FROM DVKaraoke WHERE tenDV=?";
               CallableStatement svCall11 = con.prepareCall(sqlSV11);
               svCall11.setString(1, sv1);
               ResultSet svRS11 = svCall11.executeQuery();
               while(svRS11.next())
               {
                   serviceCost += svRS11.getDouble("giaDV");

               }
               String sqlSV12 = "SELECT *FROM DVThueCaSi WHERE tenDV=?";
               CallableStatement svCall12 = con.prepareCall(sqlSV12);
               svCall12.setString(1,sv1);
               ResultSet svRS12 = svCall12.executeQuery();
               while(svRS12.next())
               {
                   serviceCost += svRS12.getDouble("giaDV");

               }
               
               //SERVICE 2
               String sqlSV2 = "SELECT *FROM DVPhoThong WHERE tenDV=?";
               CallableStatement svCall2 = con.prepareCall(sqlSV2);
               svCall2.setString(1,sv2);
               ResultSet svRS2 = svCall2.executeQuery();
               while(svRS2.next())
               {
                   serviceCost += svRS2.getDouble("giaDV");
               }
               
               String sqlSV21 = "SELECT *FROM DVKaraoke WHERE tenDV=?";
               CallableStatement svCall21 = con.prepareCall(sqlSV21);
               svCall21.setString(1,sv2);
               ResultSet svRS21 = svCall21.executeQuery();
               while(svRS21.next())
               {
                   serviceCost += svRS21.getDouble("giaDV");

               }
               String sqlSV22 = "SELECT *FROM DVThueCaSi WHERE tenDV=?";
               CallableStatement svCall22 = con.prepareCall(sqlSV22);
               svCall22.setString(1,sv2);
               ResultSet svRS22 = svCall22.executeQuery();
               while(svRS22.next())
               {
                   serviceCost += svRS22.getDouble("giaDV");
 
               }
               
               //SERVICE 3
              
               String sqlSV3 = "SELECT *FROM DVPhoThong WHERE tenDV=?";
               CallableStatement svCall3 = con.prepareCall(sqlSV3);
               svCall3.setString(1,sv3);
               ResultSet svRS3 = svCall3.executeQuery();
               while(svRS3.next())
               {
                   serviceCost += svRS3.getDouble("giaDV");

               }
               
               String sqlSV31 = "SELECT *FROM DVKaraoke WHERE tenDV=?";
               CallableStatement svCall31 = con.prepareCall(sqlSV31);
               svCall31.setString(1,sv3);
               ResultSet svRS31 = svCall31.executeQuery();
               while(svRS31.next())
               {
                   serviceCost += svRS31.getDouble("giaDV");

               }
               String sqlSV32 = "SELECT *FROM DVThueCaSi WHERE tenDV=?";
               CallableStatement svCall32 = con.prepareCall(sqlSV32);
               svCall32.setString(1,sv3);
               ResultSet svRS32 = svCall32.executeQuery();
               while(svRS32.next())
               {
                   serviceCost += svRS32.getDouble("giaDV");

               }
            }
        }
        System.out.printf("\nROOM PRICE:\t\t %,.2f VND",roomCost);
        System.out.printf("\nMENU PRICE:\t\t %,.2f VND",menuCost);
        System.out.printf("\nSERVICE PRICE:\t\t %,.2f VND",serviceCost);
        System.out.println("\n\t\t\t====================");
        System.out.printf("\nTONG BILL:\t\t %,.2f VND\n",roomCost + menuCost + serviceCost);
        String check = "SELECT *FROM DoanhThu WHERE name=?";
        CallableStatement stCheck = con.prepareCall(check);
        stCheck.setString(1,name);
        ResultSet rsCheck = stCheck.executeQuery();
        boolean flag = false;
        while(rsCheck.next())
        {
            flag = true;
        }
        if(flag == false)
        {                      
                String saveDoanhThu = "INSERT INTO DoanhThu (name,date,total) "
                            + "VALUES(?,?,?)";
                PreparedStatement stDoanhThu = con.prepareStatement(saveDoanhThu);
                stDoanhThu.setString(1, name);
                stDoanhThu.setString(2,date);
                stDoanhThu.setDouble(3, roomCost + menuCost + serviceCost);
                stDoanhThu.executeUpdate();            
        }                              
        con.close();
    }
}
    
    /**
     * 
     * @param month: tháng 
     * @param year: năm
     * @return doanh thu tháng/năm tương ứng
     * @throws SQLException
     * @throws ParseException 
     */
    public double tinhDoanhThuThang(int month,int year) throws SQLException, ParseException{
        String time = "";
        double total = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DoanhThu";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                time += rs.getString("date");
                String[] temp = time.split("\\s");
                time = temp[1];
                if(checkMonth(time)==month - 1 && checkYear(time) == year)
                {
                    total += rs.getDouble("total");
                }
            }
        }
         return total;       
    }
    
    /**
     * 
     * @param quarterYear: quý
     * @param year: năm
     * @return doanh thu theo quý tương ứng
     * @throws SQLException
     * @throws ParseException 
     */
    public double tinhDoanhThuQuy(int quarterYear, int year) throws SQLException, ParseException{
         switch(quarterYear)
         {
             case 1:
             {                                
                 return tinhDoanhThuThang(1,year) + tinhDoanhThuThang(2,year) + tinhDoanhThuThang(3,year);
             }
             case 2:
             {
                 return tinhDoanhThuThang(4,year) + tinhDoanhThuThang(5,year) + tinhDoanhThuThang(6,year);
             }
             case 3:
             {
                 return tinhDoanhThuThang(7,year) + tinhDoanhThuThang(8,year) + tinhDoanhThuThang(9,year);
             }
             case 4:
             {
                 return tinhDoanhThuThang(10,year) + tinhDoanhThuThang(11,year) + tinhDoanhThuThang(12,year);
             }
         }
        return 0; 
    }
    /**
     * @return the tenTiec
     */
  

    /**
     * @param tenTiec the tenTiec to set
     */
    public void setTenTiec(String tenTiec) {
        this.tenTiec = tenTiec;
    }

    /**
     * @return the tenSanh
     */
    public String getTenSanh() {
        return tenSanh;
    }

    /**
     * @param tenSanh the tenSanh to set
     */
    public void setTenSanh(String tenSanh) {
        this.tenSanh = tenSanh;
    }

    /**
     * @return the giaThueSanh
     */
    public double getGiaThueSanh() {
        return giaThueSanh;
    }

    /**
     * @param giaThueSanh the giaThueSanh to set
     */
    public void setGiaThueSanh(double giaThueSanh) {
        this.giaThueSanh = giaThueSanh;
    }

}
