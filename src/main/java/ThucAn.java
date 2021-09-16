
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ThucAn {       
    private String ten;
    private double gia;
    private String ghiChu;    
   
    /**
     * Hiển thị danh sách thức ăn 
     * @throws SQLException 
     */
    public void show() throws SQLException{
        System.out.println("\n\tDanh sach thuc an hien tai: \n");
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT * FROM ThucAn";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                System.out.println("\nID : " + rs.getInt(1));
                System.out.println("Ten mon: " + rs.getString(2));
                System.out.printf("Gia: %,.2f VND\n",rs.getDouble(3));
                System.out.println("Ghi chu: " + rs.getString(4));
            }
            con.close();
        }
    }
    /**
     * Kiểm tra mã thức ăn có trong danh sách hay không
     * @param id: mã thức ăn
     * @return
     * @throws SQLException 
     */
    public boolean checkID(int id) throws SQLException{        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM ThucAn WHERE id=?";
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
     * Nhập thông tin của 1 thức ăn
     * @param scan
     * @throws SQLException 
     */
    public void nhap(Scanner scan) throws SQLException{
        scan.nextLine();
        System.out.print("Nhap ten thuc an: ");
        this.ten = scan.nextLine();
        System.out.print("Nhap gia thuc an: ");
        this.gia = scan.nextDouble();
        scan.nextLine();
        System.out.print("Ghi chu: ");
        this.ghiChu = scan.nextLine(); 
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO ThucAn (TenMon,Gia,GhiChu) "
                    + "VALUES(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.ten);
            st.setDouble(2, this.gia);
            st.setString(3, this.ghiChu);
            st.executeUpdate();
            con.close();
        }
    }
    
    /**
     * Cập nhật thông tin của 1 thức ăn
     * @param id: mã thức ăn cần cập nhật
     * @param scan
     * @throws SQLException 
     */
    public void capNhat(int id, Scanner scan) throws SQLException{
        if(checkID(id) == true)
        {
            scan.nextLine();
            System.out.print("Nhap ten thuc an: ");
            this.ten = scan.nextLine();
            System.out.print("Nhap gia thuc an: ");
            this.gia = scan.nextDouble();
            scan.nextLine();
            System.out.print("Ghi chu: ");
            this.ghiChu = scan.nextLine();
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "UPDATE ThucAn SET TenMon=?,Gia=?,GhiChu=? WHERE id=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, this.ten);
                st.setDouble(2, this.gia);
                st.setString(3,this.ghiChu);
                st.setInt(4,id);
                st.executeUpdate();
            }
            System.out.println("Cap nhat thanh cong!!!\n");
        }
        else
            System.out.println("\nKhong hop le");
    }
    
    /**
     * Xóa thông tin của 1 thức ăn
     * @param id: mã thức ăn cần xóa
     * @throws SQLException 
     */
    public void xoa(int id) throws SQLException{
        if(checkID(id)==true)
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM ThucAn WHERE id=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("\nXoa thanh cong!!!\n");
                con.close();
            }
        }
        else
            System.out.println("\nKhong hop le");
    }
    
    /**
     * Tra cứu thông tin của 1 thức ăn
     * @param s: Tên thức ăn cần tra cứu
     * @throws SQLException 
     */
    public void traCuu(String s) throws SQLException{       
        int dem = 0;
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM ThucAn WHERE CHARINDEX(?,TenMon)>0";
            CallableStatement cstm = con.prepareCall(sql);
            cstm.setString(1, s);
            ResultSet rs = cstm.executeQuery();            
            while(rs.next())
            {
                ++dem;
                if(dem == 1)
                    System.out.println("\nKet qua tim duoc: \n");
                System.out.println("\nID : " + rs.getInt(1));
                System.out.println("Ten mon: " + rs.getString(2));
                System.out.printf("Gia: %,.2f VND\n",rs.getDouble(3));
                System.out.println("Ghi chu: " + rs.getString(4));
            }
                con.close();
        }
        
        if(dem == 0)
            System.out.println("\nKhong tim thay");
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the gia
     */
    public double getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(double gia) {
        this.gia = gia;
    }

    /**
     * @return the ghiChu
     */
    public String getGhiChu() {
        return ghiChu;
    }

    /**
     * @param ghiChu the ghiChu to set
     */
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
