
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Karaoke extends DichVu {
    private int thoiLuongThue; 
    
    /**
     * Hiển thị danh sách dịch vụ karaoke
     * @throws SQLException 
     */
    @Override
    public void showDV() throws SQLException{
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVKaraoke";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n\t\tDich vu karaoke: ");
            while(rs.next())
            {
                System.out.println();
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3));
                System.out.println("Thoi luong thue: " + rs.getInt(4));
            }
        }
     }
    /**
     * Tra cứu dịch vụ karaoke
     * @param s: Tên dịch vụ
     * @throws SQLException 
     */
    @Override
    public void traCuuDV(String s) throws SQLException{
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVKaraoke WHERE CHARINDEX(?,tenDV)>0";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1, s);
            ResultSet rs = cstm.executeQuery();
            System.out.println("\n\t\tDich vu karaoke: ");
            while(rs.next())
            {
                ++dem;
                if(dem == 1)
                    System.out.println("\nKet qua tim duoc: \n");
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3) + " VND");
                System.out.println("Thoi luong thue: " + rs.getInt(4)+ " phut");
            }
        }
        if(dem == 0)
            System.out.println("\nKhong tim thay!!!");
    }
    /**
     * Nhập thông tin 1 dịch vụ karaoke
     * @param scan
     * @throws SQLException 
     */
    @Override
    public void nhapDV(Scanner scan) throws SQLException {
        scan.nextLine();
        System.out.print("Ten dich vu: ");
        super.setTenDV(scan.nextLine());
        System.out.print("Gia dich vu: ");
        super.setGiaDV(scan.nextDouble());
        System.out.print("Khoang thoi gian thue: ");
        this.thoiLuongThue = scan.nextInt();
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO DVKaraoke (tenDV,giaDV,thoiLuongThue) "
                    + "VALUES(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,super.getTenDV());
            st.setDouble(2, super.getGiaDV() );
            st.setInt(3, this.thoiLuongThue);
            st.executeUpdate();
        }
    }
    /**
     * Kiểm tra mã dv karaoke
     * @param id: mã dv
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean checkID(int id) throws SQLException{        
        Connection con = ConnectDB.getSQLServerConnection();
        String sql = "SELECT *FROM DVKaraoke WHERE id=?";
        CallableStatement cstm = con.prepareCall(sql);
        cstm.setInt(1,id);
        ResultSet rs = cstm.executeQuery();
        while(rs.next())
        {
             return true;// co id
        }
        return false; // khong co id 
    }
    
    /**
     * Kiểm tra tên dịch vụ karaoke trong danh sách
     * @param s: Tên dịch vụ
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean checkName(String s) throws SQLException{        
        Connection con = ConnectDB.getSQLServerConnection();
        String sql = "SELECT *FROM DVKaraoke WHERE tenDV=?";
        CallableStatement cstm = con.prepareCall(sql);
        cstm.setString(1, s);
        ResultSet rs = cstm.executeQuery();
        while(rs.next())
        {
             return true;// co id
        }
        return false; // khong co id 
    }
    
    /**
     * Cập nhật thông tin dịch vụ
     * @param id: mã dịch vụ
     * @param scan
     * @throws SQLException 
     */
    @Override
    public void capNhat(int id,Scanner scan) throws SQLException{
       if(this.checkID(id))
       {
           try (Connection con = ConnectDB.getSQLServerConnection()) {
               scan.nextLine();
               System.out.println("Nhap lai thong tin dich vu: ");
               System.out.print("Ten dich vu: ");
               String ten = scan.nextLine();
               System.out.print("Gia dich vu: ");
               double gia = scan.nextDouble();
               System.out.print("Khoang thoi gian thue: ");
               int t = scan.nextInt();
               String sql = "UPDATE DVKaraoke SET tenDV=?,giaDV=?,thoiLuongThue=? WHERE id=?";
               PreparedStatement st = con.prepareStatement(sql);
               st.setString(1, ten);
               st.setDouble(2, gia);
               st.setInt(3, t);
               st.setInt(4, id);
               st.executeUpdate();
               System.out.println("\n\tCap nhat thanh cong!!!\n");
           }
       }
       else
           System.out.println("\nKhong hop le");
   }
    
    /**
     * Xóa thông tin dịch vụ
     * @param s: tên dịch vụ
     * @throws SQLException 
     */
    @Override
    public void xoa(String s) throws SQLException{
        if(this.checkName(s))
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM DVKaraoke WHERE tenDV=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, s);
                st.executeUpdate();
            }
            System.out.println("\n\tXoa thanh cong!");
        }
        else
            System.out.println("\nKhong hop le!!!");
    }
    
    /**
     * @return the thoiLuongThue
     */
    public int getThoiLuongThue() {
        return thoiLuongThue;
    }

    /**
     * @param thoiLuongThue the thoiLuongThue to set
     */
    public void setThoiLuongThue(int thoiLuongThue) {
        this.thoiLuongThue = thoiLuongThue;
    }
         
}
