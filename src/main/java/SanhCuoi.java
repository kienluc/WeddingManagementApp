
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SanhCuoi {   
    private String tenSanh;
    private int sucChua;
    private double giaThueSanh;
    private int viTri;
    
    /**
     * Nhập các thông tin của 1 sảnh cưới
     * @param scan
     * @throws SQLException 
     */
    public void nhapSanh(Scanner scan) throws SQLException{
        scan.nextLine();
        System.out.print("\tTen sanh: ");
        this.tenSanh = scan.nextLine();
        System.out.print("\tVi tri: ");
        this.viTri = scan.nextInt();
        System.out.print("\tSuc chua: ");
        this.sucChua = scan.nextInt();
        System.out.print("\tGia thue: ");
        this.giaThueSanh = scan.nextDouble();
        
        try ( //
                Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO SanhCuoi (tenSanh,viTri,sucChua,giaThueSanh)"
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.tenSanh);
            st.setInt(2, this.viTri);
            st.setInt(3, this.sucChua);
            st.setDouble(4, this.giaThueSanh);
            st.executeUpdate();
            con.close();
        }     
    }      
    
    /**
     * Hiển thị thông tin các sảnh hiện có
     * @throws SQLException 
     */
    public void showSanh() throws SQLException{
        System.out.println("\nDanh sach sanh hien tai: \n");
        try (Connection con = ConnectDB.getSQLServerConnection() //connect
        ) {
            String sql = "SELECT *FROM SanhCuoi";
            Statement st = con.createStatement(); 
            ResultSet rs = st.executeQuery(sql); 
            while(rs.next())// 
            {
                System.out.println("\n\nID: " + rs.getInt(1));
                System.out.printf("Ma sanh: S%03d \n",rs.getInt(1));
                System.out.println("Ten sanh: " + rs.getString(2));
                System.out.println("Vi tri: tang " + rs.getInt(3));
                System.out.println("Suc chua: "+ rs.getInt(4)+ " ban");
                System.out.printf("Gia thue: %,.2f VND\n",rs.getDouble(5));
                System.out.println("Trang thai: " + rs.getString(6));
            } 
            con.close();
        } 
    }
    
    /**
     * Kiểm tra mã sảnh
     * @param id : mã sảnh cần kiểm tra
     * @return true: có mã / false: không có mã
     * @throws SQLException 
     */
    public boolean checkID(int id) throws SQLException{        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM SanhCuoi WHERE id=?";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setInt(1,id);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                con.close();
                return true;// co id
            }
        }
        return false; // khong co id 
    }   
    
    /**
     * Cập nhật thông tin của 1 sảnh
     * @param id: mã của sảnh cần cập nhật
     * @param scan
     * @throws SQLException 
     */
    public void capNhatSanh(int id,Scanner scan) throws SQLException
    {   
        if(checkID(id)== true)
        {
            scan.nextLine();
            System.out.println("Nhap lai thong tin sanh: ");                
            System.out.print("\tTen sanh: ");
            this.tenSanh = scan.nextLine();
            System.out.print("\tVi tri: ");
            this.viTri = scan.nextInt();
            System.out.print("\tSuc chua: ");
            this.sucChua = scan.nextInt();
            System.out.print("\tGia thue: ");
            double giaThue = scan.nextDouble();
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "UPDATE SanhCuoi SET tenSanh = ?,viTri=?,sucChua=?,"
                        + "giaThueSanh=? WHERE id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, tenSanh);
                st.setInt(2, viTri);
                st.setInt(3, sucChua);
                st.setDouble(4, giaThue);
                st.setInt(5, id);
                st.executeUpdate();
                con.close();
                System.out.println("Cap nhat thanh cong!!!\n");
        }  
        }
        else
            System.out.println("\nKhong hop le");
        
     }

    
    /**
     * Xóa thông tin của 1 sảnh
     * @param id: mã sảnh cần xóa
     * @throws SQLException 
     */
    public void xoaSanh(int id) throws SQLException            
    {   
        if(checkID(id)== true)
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM SanhCuoi WHERE id=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Xoa thanh cong!!!\n");
                con.close();
            }
        }
        else
            System.out.println("\nKhong hop le");
    }
 
    /**
     * Tra cứu thông tin 1 sảnh theo tên sảnh
     * @param s: Tên sảnh 
     * @throws SQLException 
     */
    public void traCuuTheoTen(String s) throws SQLException{
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT * FROM SanhCuoi WHERE CHARINDEX(?,tenSanh)>0";
            CallableStatement cstm = con.prepareCall(sql); 
            cstm.setString(1,s);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                ++dem;
                if(dem == 1)
                    System.out.println("\nKet qua tim duoc: \n");
                System.out.println("ID: " + rs.getInt(1));
                System.out.printf("Ma sanh: S%03d \n",rs.getInt(1));
                System.out.println("Ten sanh: " + rs.getString(2));
                System.out.println("Vi tri: tang " + rs.getInt(3));
                System.out.println("Suc chua: "+ rs.getInt(4)+ " ban");
                System.out.printf("Gia thue: %,.2f VND\n\n",rs.getDouble(5));
            }
            con.close();
        }
         if(dem == 0)
             System.out.println("\n\tKhong tim thay");
    }  
    
    /**
     * Tra cứu thông tin của 1 sảnh theo sức chứa của sảnh
     * @param count: sức chứa của sảnh
     * @throws SQLException 
     */
    public void traCuuTheoSucChua(int count) throws SQLException{
         int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM SanhCuoi WHERE sucChua>=?";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setInt(1, count);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                ++dem;
                System.out.println("ID: " + rs.getInt(1));
                System.out.printf("Ma sanh: S%03d \n ",rs.getInt(1));
                System.out.println("Ten sanh: " + rs.getString(2));
                System.out.println("Vi tri: tang " + rs.getInt(3));
                System.out.println("Suc chua: "+ rs.getInt(4)+ " ban");
                System.out.printf("Gia thue: %,.2f VND\n\n",rs.getDouble(5));
            }
            con.close();
        }
         if(dem == 0)
             System.out.println("\n\tKhong tim thay");
    }
    
    /**
     * Tra cứu thông tin của 1 sảnh theo vị trí của sảnh
     * @param location: vị trí sảnh
     * @throws SQLException 
     */
    public void traCuuTheoVT(int location) throws SQLException{
         int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM SanhCuoi WHERE viTri=?";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setInt(1, location);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                ++dem;
                System.out.println("ID: " + rs.getInt(1));
                System.out.printf("Ma sanh: S%03d \n",rs.getInt(1));
                System.out.println("Ten sanh: " + rs.getString(2));
                System.out.println("Vi tri: tang " + rs.getInt(3));
                System.out.println("Suc chua: "+ rs.getInt(4)+ " ban");
                System.out.printf("Gia thue: %,.2f VND\n\n",rs.getDouble(5));
            }
            con.close();
        } 
         if(dem == 0)
             System.out.println("\n\tKhong tim thay");
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
     * @return the sucChua
     */
    public int getSucChua() {
        return sucChua;
    }

    /**
     * @param sucChua the sucChua to set
     */
    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
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

    /**
     * @return the viTri
     */
    public int getViTri() {
        return viTri;
    }

    /**
     * @param viTri the viTri to set
     */
    public void setViTri(int viTri) {
        this.viTri = viTri;
    }    
}
