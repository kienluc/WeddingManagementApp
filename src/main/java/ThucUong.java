
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ThucUong { 
    private String ten;
    private double gia;
    private String hangSX;
    
    /**
     * Hiển thị danh sách thức uống hiện có
     * @throws SQLException 
     */
    public void show() throws SQLException{
        System.out.println("\n\tDanh sach thuc uong hien tai: \n");
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT * FROM ThucUong";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                System.out.println("\nID : " + rs.getInt(1));
                System.out.println("Ten thuc uong: " + rs.getString(2));
                System.out.printf("Gia: %,.2f VND\n",rs.getDouble(3));
                System.out.println("Hang san xuat: " + rs.getString(4));
            }
        }
    }
    
    /**
     * Kiểm tra mã thức uống trong danh sách
     * @param id: mã thức uống cần tìm
     * @return
     * @throws SQLException 
     */
    public boolean checkID(int id) throws SQLException{        
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "SELECT *FROM ThucUong WHERE id=?";
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
     * Nhập thông tin của 1 thức uống
     * @param scan
     * @throws SQLException 
     */
    public void nhap(Scanner scan) throws SQLException{
        scan.nextLine();
        System.out.print("Nhap ten thuc uong: ");
        this.ten = scan.nextLine();
        System.out.print("Nhap gia thuc uong: ");
        this.gia = scan.nextDouble();
        scan.nextLine();
        System.out.print("Nhap ten hang san xuat: ");
        this.hangSX = scan.nextLine();    
        try (Connection con = ConnectDB.getSQLServerConnection()) {
            String sql = "INSERT INTO ThucUong (TenThucUong,Gia,HangSX) "
                    + "VALUES(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.ten);
            st.setDouble(2, this.gia);
            st.setString(3, this.hangSX);
            st.executeUpdate();
        }
    }
    
    /**
     * Cập nhật thông tin của 1 thức uống
     * @param id: mã thức uống cần cập nhật
     * @param scan
     * @throws SQLException 
     */
    public void capNhat(int id, Scanner scan) throws SQLException{
        if(checkID(id)== true)
        {
            scan.nextLine();
            System.out.print("Nhap ten thuc uong: ");
            this.ten = scan.nextLine();
            System.out.print("Nhap gia thuc uong: ");
            this.gia = scan.nextDouble();
            scan.nextLine();
            System.out.print("Hang san xuat: ");
            this.hangSX = scan.nextLine();
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "UPDATE ThucUong SET TenThucUong=?,Gia=?,HangSX=? WHERE id=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, this.ten);
                st.setDouble(2, this.gia);
                st.setString(3,this.hangSX);
                st.setInt(4,id);
                st.executeUpdate();
            }
            System.out.println("Cap nhat thanh cong!!!\n");
        }
        else
            System.out.println("\nKhong hop le");
    }
    
    /**
     * Xóa thông tin thức uống trong danh sách
     * @param id: mã thức uống cần xóa
     * @throws SQLException 
     */
    public void xoa(int id) throws SQLException{
        if(checkID(id)==true)
        {
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "DELETE FROM ThucUong WHERE id=?";
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
     * Tra cứu thông tin thức uống theo TÊN 
     * @param s: Tên thức uống
     * @throws SQLException 
     */
    public void traCuu(String s) throws SQLException{
        int dem = 0;
            try (Connection con = ConnectDB.getSQLServerConnection()) {
                String sql = "SELECT *FROM ThucUong WHERE CHARINDEX(?,TenThucUong)>0";
                CallableStatement cstm = con.prepareCall(sql);
                cstm.setString(1, s);
                ResultSet rs = cstm.executeQuery();
                while(rs.next())
                {
                    ++dem;
                    if(dem == 1)
                        System.out.println("\nKet qua tim duoc: \n");
                    System.out.println("\nID : " + rs.getInt(1));
                    System.out.println("Ten thuc uong: " + rs.getString(2));
                    System.out.printf("Gia: %,.2f VND\n",rs.getDouble(3));
                    System.out.println("Hang san xuat: " + rs.getString(4));
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
     * @return the hangSX
     */
    public String getHangSX() {
        return hangSX;
    }

    /**
     * @param hangSX the hangSX to set
     */
    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }
    
}

