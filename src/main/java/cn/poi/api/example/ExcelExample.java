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
package cn.poi.api.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JoinRowSet;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.poi.api.command.BrandConfigCommand;

public class ExcelExample {

    private static ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

    private static final String sqlStart = "INSERT INTO au_store_deliarea (store_id,brand_code ,store_code ,area_id,area_name)SELECT aut.id AS store_id, aut.brand_code,aut.code AS store_code,sa.id AS area_id,'";

    private static final String sqlEnd = "' FROM au_store  aut,sys_area sa WHERE ";

    private static final String provice = " sa.province = '";

    private static final String city = "' and sa.city = '";

    private static final String area = "' and sa.district = '";

    private static final String code = "' and aut.CODE = '";

    private static final String brand_code = "' and aut.brand_code = '";

    public static void main(String[] args) {
        try {
            ReadExcel("classpath:驻店宝品牌配置信息表.xlsx", "columbia香港");
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void ReadExcel(String excel, String brandcode) throws EncryptedDocumentException, InvalidFormatException, IOException {
        List<BrandConfigCommand> list = new ArrayList<>();
        InputStream inp = resourceLoader.getResource(excel).getInputStream();
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(4);
        Row tempRow = null;
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            BrandConfigCommand brandConfigCommand = new BrandConfigCommand();
            String[] array = new String[5];
            Row row = sheet.getRow(i);
            if (StringUtils.isEmpty(row.getCell(4).toString())) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (j == 0 && StringUtils.isEmpty(row.getCell(j).toString())) {
                    System.out.print(tempRow.getCell(j).getStringCellValue() + "    ");
                    array[j] = tempRow.getCell(0).getStringCellValue();
                    continue;
                } else if (j == 0) {
                    tempRow = row;
                }
                if (j == 1 && StringUtils.isEmpty(row.getCell(j).toString())) {
                    System.out.print(tempRow.getCell(j).getStringCellValue() + "    ");
                    array[j] = tempRow.getCell(j).getStringCellValue();
                    continue;
                }
                array[j] = row.getCell(j).getStringCellValue();
                System.out.print(row.getCell(j).toString() + "    ");

            }

            brandConfigCommand.setStoreCode(array[0]);
            brandConfigCommand.setStoreDate(array[1]);
            brandConfigCommand.setProvice(array[2]);
            brandConfigCommand.setCity(array[3]);
            brandConfigCommand.setArea(array[4]);
            brandConfigCommand.setArea_name(array[2] + array[3] + array[4]);
            list.add(brandConfigCommand);
            System.out.println();
        }

        List<String> listStr = new ArrayList<>();
        for (BrandConfigCommand brandConfigCommand : list) {
            String str = sqlStart + brandConfigCommand.getArea_name() + sqlEnd + provice + brandConfigCommand.getProvice() + city + brandConfigCommand.getCity() + area + brandConfigCommand.getArea() + code + brandConfigCommand.getStoreCode() + brand_code
                    + brandcode + "'";
            listStr.add(str);
        }
        System.out.println(JSON.toJSONString(listStr));
       
    }


}
