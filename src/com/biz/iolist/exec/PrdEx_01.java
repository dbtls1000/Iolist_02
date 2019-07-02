package com.biz.iolist.exec;

import java.util.Scanner;

import com.biz.iolist.serevice.ProductService;

public class PrdEx_01 {
	public static void main(String[] args) {
		ProductService ps = new ProductService();
		Scanner scan = new Scanner(System.in);
		while(true) {
			ps.viewProduct();
			System.out.println("업무를 선택해 주십시오");
			System.out.println("======================================================");
			System.out.println("1.상품추가\t2.상품수정\t3.상품삭제\t4.종료");
			System.out.println("======================================================");
			System.out.print("업무선택 >>");
			String strMenu = scan.nextLine();
			int intMenu = Integer.valueOf(strMenu);
			if(intMenu == 1) ps.insertPRD();
			if(intMenu == 2) ps.updatePRD();
			if(intMenu == 3) ps.deletePRD();
			if(intMenu == 4) break;
		}
	}
}
