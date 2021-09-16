
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Play {
    //Bien lua chon chuc nang
    public static int choice;
    
    public static void main(String[] args) throws SQLException, ParseException{       
        //Data object
        Scanner scan = new Scanner(System.in); 
        SanhCuoi dsSanh = new SanhCuoi(); 
        DichVu dv = new DichVu();
        DichVu ka = new Karaoke();
        DichVu cs = new CaSi();
        ThucAn ta = new ThucAn();
        ThucUong tu = new ThucUong();
        TiecCuoi tiec = new TiecCuoi();

        //Option Menu Cac Chuc Nang Quan Ly
                     
        do{
            String options = "===============QUAN LY TIEC CUOI===============\n"
                + "1. Quan ly thong tin sanh cuoi\n"
                + "2. Quan ly thong tin dich vu\n"
                + "3. Quan ly thong tin thuc an - thuc uong\n"
                + "4. Quan ly thong tin tiec\n"
                + "5. Thanh toan\n"
                + "6. Xem doanh thu\n"
                + "7. Dong chuong trinh\n";
            System.out.println(options);
            System.out.print("Ban chon: ");
            choice = scan.nextInt();
            switch(choice)
            {               
                case 1://Quan ly thong tin sanh - Chuc nang 1
                {
                    String options1 = "\t1. Them\n"
                            + "\t2. Cap nhat\n"
                            + "\t3. Xoa\n"
                            + "\t4. Tra cuu\n"
                            + "\t5. Xem danh sach";
                            
                        System.out.println("====Quan ly thong tin sanh cuoi\n" 
                                 + options1);
                        System.out.print("Ban chon: ");
                        choice = scan.nextInt();
                        switch(choice)
                        {                            
                            case 1://them sanh
                            {                                
                                System.out.println("@Them sanh\nNhap thong tin "
                                        + "sanh: ");                                                             
                                dsSanh.nhapSanh(scan);                                
                                System.out.println("\n\tThem thanh cong !!! "
                                        + "Danh sach sanh hien tai: \n"); 
                                dsSanh.showSanh();
                                break;
                            }
                            case 2://Cap nhat sanh
                            {                                        
                                dsSanh.showSanh();
                                System.out.print("@Cap nhat sanh\nNhap ID sanh "
                                        + "muon cap nhat: ");
                                scan.nextLine();
                                int id = scan.nextInt();
                                if(dsSanh.checkID(id))                                   
                                    dsSanh.capNhatSanh(id, scan); 
                                else
                                    System.out.println("\nKhong hop le");
                                break;
                            }
                            case 3://Xoa sanh
                            {                                                                                                 
                                scan.nextLine();
                                dsSanh.showSanh();
                                System.out.print("@Xoa sanh\nNhap ID sanh muon "
                                    + "xoa: ");
                                int id = scan.nextInt();
                                if(dsSanh.checkID(id))                                    
                                    dsSanh.xoaSanh(id); 
                                else
                                    System.out.println("\nKhong hop le");
                                break;
                            } 
                            case 4://Tra cuu sanh
                            {                                                                                              
                                 scan.nextLine();
                                 System.out.print("@Tra cuu\n\t@@ 1/ Theo ten"
                                        + "\n\t@@ 2/ Theo suc chua"
                                        + "\n\t@@ 3/ Theo vi tri sanh"
                                        + "\n\t@@ 4/ Thoat"
                                        + "\nBan chon: ");
                                    choice = scan.nextInt();
                                    switch(choice)
                                    {
                                        case 1: //tra cuu theo ten
                                        {  
                                            scan.nextLine();
                                            System.out.print("Nhap ten sanh "
                                                + "muon tim: ");
                                            String s = scan.nextLine();
                                            System.out.println();
                                            dsSanh.traCuuTheoTen(s);                                        
                                            break;
                                        }
                                        case 2://tra cuu theo suc chua
                                        {
                                            scan.nextLine();
                                            System.out.print("Nhap suc chua cua "
                                                 + "sanh: ");
                                            int count = scan.nextInt();
                                            System.out.println();
                                            dsSanh.traCuuTheoSucChua(count);
                                            break;
                                        
                                        }
                                        case 3://tra cuu theo vi tri sanh
                                        {
                                            scan.nextLine();
                                            System.out.print("Nhap vi tri sanh: ");
                                            int location = scan.nextInt(); 
                                            System.out.println();
                                            dsSanh.traCuuTheoVT(location);
                                            break;
                                        }
                                        case 4:
                                        {
                                            break;
                                        }
                                    }
                                
                                break;                               
                            }
                            case 5: //Xem danh sach
                            {
                                dsSanh.showSanh();
                                break;
                            }
                        }
                    break;
                }
                           
                
                case 2://Quan ly thong tin dich vu - Chuc nang 2
                {
                    String options2 = "\n1. Them dich vu"
                            + "\n2. Cap nhat dich vu"
                            + "\n3. Xoa dich vu"
                            + "\n4. Tra cuu theo ten dich vu"                           
                            + "\n5. Xem danh sach dich vu";
                    System.out.println("=======Quan ly thong tin dich vu: "+
                            options2);
                    System.out.print("Ban chon: ");
                    choice = scan.nextInt();
                    switch(choice)
                    {                      
                        case 1://Them dich vu
                        {
                            System.out.println("\n@Them dich vu"
                                    + "\n\t@@ 1/ DV Karaoke"
                                    + "\n\t@@ 2/ DV Thue ca si"
                                    + "\n\t@@ 3/ DV Pho Thong");
                                    
                            System.out.print("Ban chon: ");
                            choice = scan.nextInt();
                            switch(choice)
                            {
                                case 1:// them DV karaoke
                                {
                                    System.out.println("@Them dich vu karaoke");
                                    ka.nhapDV(scan);                                   
                                    System.out.println("\n\tThem dich vu karaoke"
                                            + " thanh cong !!!");                                    
                                    break;
                                }
                                case 2://Them DV thue ca si
                                {
                                    System.out.println("@Them dich vu thue ca si");
                                    cs.nhapDV(scan);                                  
                                    System.out.println("\n\tThem dich vu thue ca si"
                                            + " thanh cong !!!");                                                      
                                    
                                    break;
                                }
                                case 3://Them DV pho thong
                                {
                                    System.out.println("@Them dich vu pho thong");
                                    dv.nhapDV(scan);                                  
                                    System.out.println("\n\tThem dich vu thanh cong!!!");                                                     
                                    break;
                                }                                                               
                            } 
                            break;
                        }
                        case 2: //Cap nhat dich vu                           
                        {
                            System.out.println("\n@Cap nhat dich vu"
                                    + "\n\t@@ 1/ DV Karaoke"
                                    + "\n\t@@ 2/ DV Thue ca si"
                                    + "\n\t@@ 3/ DV Pho thong (Trang tri phoi canh)");
                                    
                            System.out.print("Ban chon: ");
                            choice = scan.nextInt();
                            switch(choice)
                            {
                                case 1:// cap nhat dich vu karaoke                                  
                                {    
                                    ka.showDV();
                                    System.out.print("\n@Cap nhat dv karaoke\nNhap ma dich vu: ");
                                    int b = scan.nextInt();
                                    ka.capNhat(b, scan);                                   
                                    break;                                    
                                }
                                case 2:// cap nhat thue si
                                {
                                    cs.showDV();
                                    System.out.print("\n@Cap nhat dv thue ca si\nNhap ma dich vu: ");
                                    int b = scan.nextInt();
                                    cs.capNhat(b, scan);
                                    break;
                                }
                                case 3:// cap nhat dich vu pho thong
                                {
                                    dv.showDV();
                                    System.out.print("\n@Cap nhat dv pho thong\nNhap ma dich vu: ");
                                    int b = scan.nextInt();
                                    dv.capNhat(b, scan);
                                    break;
                                }
                                    
                            }                                                                                     
                            break;
                        }
                        case 3: //Xoa dich vu
                        {
                           System.out.println("\n@Xoa dich vu"
                                    + "\n\t@@ 1/ DV Karaoke"
                                    + "\n\t@@ 2/ DV Thue ca si"
                                    + "\n\t@@ 3/ DV Pho thong (Trang tri phoi canh)");
                                    
                            System.out.print("Ban chon: ");
                            choice = scan.nextInt();
                            switch(choice)
                            {
                                case 1: //xoa dv karaoke
                                {
                                    ka.showDV();
                                    scan.nextLine();
                                    System.out.print("\n@Xoa DV karaoke:\nNhap ten dich vu: ");
                                    String s = scan.nextLine();
                                    ka.xoa(s);
                                    System.out.println();
                                    break;
                                }
                                case 2: // xoa dv thue ca si
                                {
                                    cs.showDV();
                                    scan.nextLine();
                                    System.out.print("\n@Xoa DV thue ca si:\nNhap ten dich vu: ");
                                    String s = scan.nextLine();
                                    cs.xoa(s);
                                    System.out.println();                                  
                                    break;
                                }
                                case 3://xoa dv pho thong
                                {
                                    dv.showDV();
                                    scan.nextLine();
                                    System.out.print("\n@Xoa DV pho thong:\nNhap ten dich vu: ");
                                    String s = scan.nextLine();
                                    dv.xoa(s);
                                    System.out.println();  
                                    break;
                                }
                            }                                                           
                            break;
                        }
                        case 4: //Tra cuu dich vu theo ten
                        {                          
                             scan.nextLine();
                             System.out.print("\n@Tra cuu DV:\nNhap ten dich vu: ");
                             String s = scan.nextLine();
                             dv.traCuuDV(s);
                             ka.traCuuDV(s);
                             cs.traCuuDV(s);
                             break;
                        }
                        case 5://Xem danh sach dich vu
                        {
                            ka.showDV();
                            cs.showDV();
                            dv.showDV();                         
                            break;
                        }
                    }
                   break;
                }
                
                
                case 3://QUAN LY THONG TIN THUC AN - THUC UONG - CHUC NANG 3
                {                 
                  String options3 = "\t1. Them\n"
                            + "\t2. Cap nhat\n"
                            + "\t3. Xoa\n"
                            + "\t4. Tra cuu\n"
                            + "\t5. Xem danh sach";
                  System.out.println("=====Quan ly thong tin thuc an - thuc uong\n" + options3);
                  System.out.print("Ban chon: ");
                  choice = scan.nextInt();
                  switch(choice)
                  {                     
                      case 1://Them 
                      {
                          System.out.print("@ 1/ Them thuc an\n"
                                  + "@ 2/ Them thuc uong\n"
                                  + "\nBan chon: ");
                          choice = scan.nextInt();
                          switch(choice)
                          {
                              case 1://Them thuc an
                              {
                                  ThucAn a = new ThucAn();
                                  System.out.println("@Them thuc an: \nNhap thong tin thuc an");
                                  a.nhap(scan);
                                  System.out.println("\n\tThem thanh cong!!!\n");                                 
                                  break;
                              }
                              case 2://Them thuc uong
                              {
                                 ThucUong a = new ThucUong();
                                 System.out.println("@Them thuc uong: \nNhap thong tin thuc uong");
                                 a.nhap(scan);
                                 System.out.println("\n\tThem thanh cong!!!\n");                               
                                 break;
                              }                              
                          }
                          break;
                      }
                      case 2://Cap nhat danh sach 
                      {
                        
                         System.out.print("@ 1/ Cap nhat thuc an\n"
                                  + "@ 2/ Cap nhat thuc uong\n"
                                  + "\nBan chon: ");
                          choice = scan.nextInt();
                          switch(choice)
                          {
                              case 1:// cap nhat thuc an
                              {                                 
                                  ta.show();
                                  System.out.print("\nNhap ID thuc an cap nhat: ");
                                  int id = scan.nextInt();
                                  ta.capNhat(id, scan);
                                  ta.show();
                                  break;
                              }
                              case 2:// cap nhat thuc uong
                              {                                
                                 tu.show();
                                 System.out.print("\nNhap ID thuc uong cap nhat: ");
                                 int id = scan.nextInt();
                                 tu.capNhat(id,scan);
                                 tu.show();
                                 break;  
                              }
                          }
                        break;
                      }
                      
                      case 3:// Xoa
                      {
                          System.out.print("@ 1/ Xoa thong tin thuc an\n"
                                  + "@ 2/ Xoa thong tin thuc uong\n"
                                  + "\nBan chon: ");
                          choice = scan.nextInt();
                          switch(choice)
                          {
                              case 1: // Xoa thuc an
                              {
                                  ta.show();
                                  System.out.print("\nNhap ID thuc an muon xoa: ");
                                  int id = scan.nextInt();
                                  ta.xoa(id);                                  
                                  ta.show();
                                  break;
                              }
                              case 2: // Xoa thuc uong
                              {
                                  tu.show();
                                  System.out.print("\nNhap ID thuc uong muon xoa: ");
                                  int id = scan.nextInt();
                                  tu.xoa(id);
                                  tu.show();
                                  break;
                              }
                          }
                          break;
                      }
                      case 4: // tra cuu thuc an - thuc uong
                      {
                          System.out.print("@ 1/ Tra cuu thuc an\n"
                                  + "@ 2/ Tra cuu thuc uong\n"
                                  + "\nBan chon: ");
                          choice = scan.nextInt();
                          switch(choice)
                          {
                              case 1:
                              {
                                  scan.nextLine();
                                  System.out.print("\n@Tra cuu thuc an\nNhap ten thuc an can tim: ");
                                  String s = scan.nextLine();
                                  ta.traCuu(s);
                                  break;
                              }
                              case 2:
                              {
                                  scan.nextLine();
                                  System.out.print("\n@Tra cuu thuc uong\nNhap ten thuc uong can tim: ");
                                  String s = scan.nextLine();
                                  tu.traCuu(s);
                                  break;
                              }
                          }
                          break;
                      }
                      case 5:
                      {
                          ta.show();
                          tu.show();
                          break;
                      }
                  }                  
                  break;
                }
                
               
                
                
                case 4://    QUAN LY THONG TIN TIEC - CHUC NANG 4
                {                    
                  System.out.println("=====DAT TIEC=====");
                  System.out.print("\n1. Dat tiec"
                          + "\n2. Xem danh sach tiec"
                          + "\nBan chon: ");
                  choice = scan.nextInt();
                  switch(choice)
                  {
                      case 1: //DAT TIEC
                      {
                          tiec.order(scan, dsSanh);                           
                          break;
                      }
                      case 2: //SHOW DANH SACH
                      {
                          tiec.show();                        
                          break;
                      }                                               
                  }
                                 
                  break;                   
                }
                
                
                case 5:// THANH TOAN- CHUC NANG 5
                {
                  tiec.show();                
                  tiec.thanhToan(scan);
                  break;                   
                }
                
                
                
                case 6: //BAO CAO DOANH THU - CHUC NANG 6
                {                                                        
                    System.out.print("\n\tXEM DOANH THU: "
                            + "\n1. Doanh thu thang"
                            + "\n2. Doanh thu quy"
                            + "\nBan chon: ");
                    choice = scan.nextInt();
                    switch(choice)
                    {
                        case 1:
                        {
                            System.out.print("\nDOANH THU THANG\nNhap thang "
                                    + "muon tinh doanh thu: ");
                            int month = scan.nextInt();
                            System.out.print("\nNam tuong ung: ");
                            int year = scan.nextInt();
                            System.out.printf("\n\t\tDOANH THU THANG %d: %,.2f VND\n"
                                  ,month,tiec.tinhDoanhThuThang(month,year));
                            break;
                        }
                        case 2:
                        {
                            System.out.print("\nDOANH THU QUY\nNhap quy "
                                    + "muon tinh doanh thu: ");
                            int quarterYear = scan.nextInt();
                            System.out.print("\nNam tuong ung: ");
                            int year = scan.nextInt();
                            System.out.printf("\n\t\tDOANH THU QUY %d: %,.2f VND\n"
                                ,quarterYear,tiec.tinhDoanhThuQuy(quarterYear,year));
                            break;
                        }                         
                    }
                    break;
                }
                case 7: //THOAT
                {
                    System.out.println("\nDONG CHUONG TRINH!!!!\n");
                    break;
                }
                default:
                    break;
            }
        }while(choice >=1 && choice <=6);
        //
    }
}
