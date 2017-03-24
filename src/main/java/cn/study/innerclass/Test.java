/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package cn.study.innerclass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {
    public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
        /*Exsample ex = new Exsample();
        Exsample.PublicInnerClass pub = ex.new PublicInnerClass();
        pub.setPuAge(111);
        pub.test();*/

        /*Workbook book = new HSSFWorkbook();
        Cell cell = book.createSheet().createRow(1).createCell(1);
        cell.setCellValue(5l);
        System.out.println(cell.getCellType());*/
        
        FileInputStream fin = new FileInputStream("E:/test.xlsx");
        Workbook book = WorkbookFactory.create(fin);
        Sheet sheet = book.getSheetAt(0);
        ;
        PoiUtil.getCellData(sheet.getRow(0).getCell(0), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(0).getCell(1), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(0).getCell(2), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(1).getCell(0), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(1).getCell(1), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(1).getCell(2), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(2).getCell(0), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(2).getCell(1), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(2).getCell(2), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(3).getCell(0), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(3).getCell(1), new XSSFFormulaEvaluator((XSSFWorkbook)book));
        PoiUtil.getCellData(sheet.getRow(3).getCell(2), new XSSFFormulaEvaluator((XSSFWorkbook)book));
    }
}
