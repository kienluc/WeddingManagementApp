
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CaSi extends DichVu {
    private String tenCS;
    private int soLuongBH;

    @Override
    public void showDV() throws SQLException{
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVThueCaSi";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n\t\tDich vu thue ca si: ");
            while(rs.next())
            {
                System.out.println();
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3));
                System.out.println("Ten ca si: " + rs.getString(4));
                System.out.println("So luong bai hat: " + rs.getInt(5));
            }
            con.close();
        }                
    }
    
    @Override
    public void traCuuDV(String s) throws SQLException{
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVThueCaSi WHERE CHARINDEX(?,tenDV)>0";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1, s);
            ResultSet rs = cstm.executeQuery();
            System.out.println("\n\t\tDich vu thue ca si: ");
            while(rs.next())
            {++dem;
                if(dem == 1)
                    System.out.println("\nKet qua tim duoc: \n");
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3) + " VND");
                System.out.println("Ten ca si: " + rs.getString(4));
                System.out.println("So luong bai hat: " + rs.getInt(5));
            }
        }
        if (dem == 0)
            System.out.println("\nKhong tim thay !!!");
    }
    @Override
    public void nhapDV(Scanner scan) throws SQLException {
        scan.nextLine();
        System.out.print("Ten dich vu: ");
        super.setTenDV(scan.nextLine());
        System.out.print("Gia dich vu: ");
        super.setGiaDV(scan.nextDouble());
        scan.nextLine();
        System.out.print("Ten ca si: ");
        this.tenCS = scan.nextLine();
        System.out.print("So luong bai hat: ");
        this.soLuongBH = scan.nextInt();
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO DVThueCaSi (tenDV,giaDV,tenCS,soLuongBH) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, super.getTenDV());
            st.setDouble(2, super.getGiaDV());
            st.setString(3, this.tenCS);
            st.setInt(4,this.soLuongBH);
            st.executeUpdate();
        }
    }
    @Override
    public boolean checkID(int id) throws SQLException{        
        Connection con = ConnectDB.getSQLServerConnection();
        String sql = "SELECT *FROM DVThueCaSi WHERE id=?";
        CallableStatement cstm = con.prepareCall(sql);
        cstm.setInt(1,id);
        ResultSet rs = cstm.executeQuery();
        while(rs.next())
        {
             return true;// co id
        }
        return false; // khong co id 
    }
    @Override
    public boolean checkName(String s) throws SQLException{        
        Connection con = ConnectDB.getSQLServerConnection();
        String sql = "SELECT *FROM DVThueCaSi WHERE tenDV=?";
        CallableStatement cstm = con.prepareCall(sql);
        cstm.setString(1, s);
        ResultSet rs = cstm.executeQuery();
        while(rs.next())
        {
             return true;// co id
        }
        return false; // khong co id 
    }
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
               scan.nextLine();
               System.out.print("Ten ca si: ");
               String cs = scan.nextLine();
               System.out.print("So luong bai hat: ");
               int sl = scan.nextInt();
               String sql = "UPDATE DVThueCaSi SET tenDV=?,giaDV=?,tenCS =?,soLuongBH=?"
                       + " WHERE id=?";
               PreparedStatement st = con.prepareStatement(sql);
               st.setString(1, ten);
               st.setDouble(2, gia);
               st.setString(3,cs );
               st.setInt(4, sl);
               st.setInt(5,id);
               st.executeUpdate();
               System.out.println("\n\tCap nhat thanh cong!!!\n");
           }
       }
       else
           System.out.println("\nKhong hop le");
   }
    
    @Override
    public void xoa(String s) throws SQLException{
        if(this.checkName(s))
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM DVThueCaSi WHERE tenDV=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, s);
                st.executeUpdate();
            }
            System.out.println("\n\tXoa thanh cong!!!");
        }
        else
            System.out.println("\nKhong hop le!!!");
    }
    /**
     * @return the tenCS
     */
    public String getTenCS() {
        return tenCS;
    }

    /**
     * @param tenCS the tenCS to set
     */
    public void setTenCS(String tenCS) {
        this.tenCS = tenCS;
    }

    /**
     * @return the soLuongBH
     */
    public int getSoLuongBH() {
        return soLuongBH;
    }

    /**
     * @param soLuongBH the soLuongBH to set
     */
    public void setSoLuongBH(int soLuongBH) {
        this.soLuongBH = soLuongBH;
    }

    
    
    
}
