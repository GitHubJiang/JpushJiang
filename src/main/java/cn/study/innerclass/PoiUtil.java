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
import java.io.IOException;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtil {
    
    public static Workbook createXSSFWorkbook(){        
        return new XSSFWorkbook();
    }

    public static Workbook createHSSFWorkbook(){        
        return new HSSFWorkbook();
    }
    
    public static Workbook createWorkbook(FileInputStream fis) throws EncryptedDocumentException, InvalidFormatException, IOException{        
        return WorkbookFactory.create(fis);
    }
    
    public static Object getCellData(Cell cell, FormulaEvaluator formula) {        
        if(cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            System.out.println(cell.getRichStringCellValue().getString());
            return cell.getRichStringCellValue().getString();
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                System.out.println(cell.getDateCellValue());
                return cell.getDateCellValue();
            } else {
                System.out.println(cell.getNumericCellValue());
                return cell.getNumericCellValue();
            }
        case Cell.CELL_TYPE_BOOLEAN:
            System.out.println(cell.getBooleanCellValue());
            return cell.getBooleanCellValue();
        case Cell.CELL_TYPE_FORMULA:
            System.out.println(cell.getStringCellValue());
            
            switch (formula.evaluate(cell).getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    System.out.println(formula.evaluate(cell).getStringValue());
                    return formula.evaluate(cell).getStringValue();
                case Cell.CELL_TYPE_NUMERIC:
                     System.out.println(formula.evaluate(cell).getNumberValue());
                     return formula.evaluate(cell).getNumberValue();                    
                case Cell.CELL_TYPE_BOOLEAN:
                    System.out.println(formula.evaluate(cell).getBooleanValue());
                    return formula.evaluate(cell);    
                
            }
        default:
            return null;
        }
    } 
    
    public static void setCellData(Cell cell){
        cell.setCellValue(5);
    }
}
