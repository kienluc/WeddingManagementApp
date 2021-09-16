
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DichVu {
    private String tenDV;
    private double giaDV;
    
    public void showDV() throws SQLException{       
        //
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            //
            String sql = "SELECT *FROM DVPhoThong";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n\t\tDich vu pho thong: ");
            while(rs.next())
            {
                System.out.println();
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3));
            }
            con.close();
        }
    }
    public void traCuuDV(String s) throws SQLException{
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVPhoThong WHERE CHARINDEX(?,tenDV)>0";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1, s);
            ResultSet rs = cstm.executeQuery();
            System.out.println("\n\t\tDich vu pho thong: ");
            while(rs.next())
            {
                ++dem;
                if(dem == 1)
                    System.out.println("\nKet qua tim duoc: \n");
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Ten: "+ rs.getString(2));
                System.out.println("Gia dich vu: " + rs.getInt(3) + " VND");
            }
            con.close();
        }
        if(dem == 0)
            System.out.println("\nKhong tim thay!!!");
    }
    public void nhapDV(Scanner scan) throws SQLException{
        scan.nextLine();
        System.out.print("Ten dich vu: ");
        this.tenDV = scan.nextLine();
        System.out.print("Gia dich vu: ");
        this.giaDV = scan.nextDouble();
        try ( //
                Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO DVPhoThong (tenDV,giaDV) "
                    + "VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.tenDV);
            st.setDouble(2, this.giaDV);
            st.executeUpdate();
            con.close();
        }
    }
    public boolean checkID(int id) throws SQLException{        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVPhoThong WHERE id=?";
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
    public boolean checkName(String s) throws SQLException{        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM DVPhoThong WHERE tenDV=?";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1, s);
            ResultSet rs = cstm.executeQuery();
            while(rs.next())
            {
                con.close();
                return true;// co id
            }
        }
        return false; // khong co id 
    }
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
               String sql = "UPDATE DVPhoThong SET tenDV=?,giaDV=? WHERE id=?";
               PreparedStatement st = con.prepareStatement(sql);
               st.setString(1, ten);
               st.setDouble(2, gia);
               st.setInt(3, id);
               st.executeUpdate();
               System.out.println("\n\tCap nhat thanh cong!!!\n");
               con.close();
           }
       }
       else
           System.out.println("\nKhong hop le");
   }
    
   public void xoa(String s) throws SQLException{
        if(this.checkName(s))
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM DVPhoThong WHERE tenDV=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, s);
                st.executeUpdate();
                con.close();
            }
            System.out.println("\n\tXoa thanh cong!!!");
        }
        else
            System.out.println("\nKhong hop le!!!");
    }
      
 

    /**
     * @return the tenDV
     */
    public String getTenDV() {
        return tenDV;
    }

    /**
     * @param tenDV the tenDV to set
     */
    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    /**
     * @return the giaDV
     */
    public double getGiaDV() {
        return giaDV;
    }

    /**
     * @param giaDV the giaDV to set
     */
    public void setGiaDV(double giaDV) {
        this.giaDV = giaDV;
    }
    
    
}
