package com.biz.iolist.serevice;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.ProductDao;
import com.biz.iolist.model.ProductVO;

/*
 * 상품정도의 등록 수정 삭제 method
 */
public class ProductService {
	SqlSession sqlSession = null;
	ProductDao pDao = null;
	Scanner scan;

	public ProductService() {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		pDao = (ProductDao) sqlSession.getMapper(ProductDao.class);
		scan = new Scanner(System.in);
	}

	public void viewProduct() {
		System.out.println("=================================================");
		System.out.println("상품코드\t상품이름\t매입가격\t매출가격");
		System.out.println("-------------------------------------------------");
		List<ProductVO> pdrList = pDao.selectAll();
		
		for (ProductVO vo : pdrList) {
			System.out.printf("%s\t\t%s\t\t%d\t%d\n", 
						vo.getP_code(), 
						vo.getP_name(), 
						vo.getP_iprice(), 
						vo.getP_oprice());
		}
		
		System.out.println("=================================================");
	}

	public boolean insertPRD() {
		System.out.println("상품을 추가합니다");
		System.out.println("-------------------------------------------------");

		System.out.print("상품코드 >>");
		String strPcode = scan.nextLine();

		System.out.print("상품이름 >>");
		String strPname = scan.nextLine();
		int intIprice = 0;
		while (true) {
			System.out.print("상품매입가격 >>");
			String strIprice = scan.nextLine();
			try {
				intIprice = Integer.valueOf(strIprice);
			} catch (NumberFormatException e) {
				System.out.println("매입단가는 숫자만 입력하세요");
				continue;
			}
			break;
		}
		int intOprice = 0;
		while (true) {
			System.out.print("상품매출가격 >>");
			String strOprice = scan.nextLine();
			try {
				intOprice = Integer.valueOf(strOprice);
			} catch (NumberFormatException e) {
				System.out.println("매출단가는 숫자만 입력하세요");
				continue;
			}
			break;
		}
		System.out.println("-------------------------------------------------");

		ProductVO vo = new ProductVO(strPcode, strPname, intIprice, intOprice);
		System.out.println(vo);
		if (pDao.insert(vo) > 0)
			return true;
		else
			return false;

	}

	public boolean updatePRD() {
		System.out.println("상품정보를 수정 합니다");
		System.out.println("-------------------------------------------------");
		System.out.print("수정할 상품 >>");
		String strPcode = scan.nextLine();
		ProductVO vo = pDao.findByPcode(strPcode);
		if(vo==null) {
			System.out.println("상품정보가 없습니다");
			return false;
		}
		
		System.out.printf("상품이름 (%s)>>",vo.getP_name());
		String strName = scan.nextLine();
		if(strName.isEmpty()) strName= vo.getP_name();
		System.out.printf("매입가격 (%d)>>",vo.getP_iprice());
		String strIprice = scan.nextLine();
		int iPrice = 0;
		if(strIprice.isEmpty()) iPrice = vo.getP_iprice();
		else  iPrice = Integer.valueOf(strIprice);
		System.out.printf("매출가격 (%d)>>",vo.getP_oprice());
		String strOprice = scan.nextLine();
		int oPrice = 0;
		if(strOprice.isEmpty()) oPrice = vo.getP_oprice();
		else oPrice = Integer.valueOf(strOprice);
		
		vo.setP_name(strName);
		vo.setP_iprice(iPrice);
		vo.setP_oprice(oPrice);
		
		if(pDao.update(vo)>0)	return true;
		else return false;
	}

	public boolean deletePRD() {
		System.out.println("상품정보를 삭제합니다");
		System.out.println("-------------------------------------------------");
		System.out.print("삭제할 상품 >>");
		String strPcode = scan.nextLine();
		ProductVO vo = pDao.findByPcode(strPcode);
		System.out.println(vo);
		System.out.print("정말로 삭제하시겠습니까 ? 1.Yes 2.No");
		String confirm = scan.nextLine();
		if(confirm.equals("1")) {
			if(pDao.delete(strPcode)>0) return true;
			else return false;
		}
		return false;
		
	}

}
