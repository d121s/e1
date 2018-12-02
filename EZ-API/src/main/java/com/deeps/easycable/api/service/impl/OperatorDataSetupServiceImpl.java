package com.deeps.easycable.api.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.OperatorDataSetupService;

@Service
public class OperatorDataSetupServiceImpl implements OperatorDataSetupService {

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	@Autowired
	CustomerRepo custRepo;

	public ServiceResponse setupOperatorData(MultipartFile file, Long operatorId) {
		int count = 0;
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			Sheet sheet = workbook.getSheetAt(0);
			System.out.println(sheet.getHeader());
			//DataFormatter dataFormatter = new DataFormatter();
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");

			// Create a new Operator to be Deleted later
			Operator op = new Operator();
			op.setName(file.getOriginalFilename().replaceAll(".xls", ""));
			op.setSubscriptionCost(100);
			op.setSubscriptionStatus("Active");
			opRepo.save(op);

			for (Row row : sheet) {
				if (count > 0) {
					String servicePackageName = row.getCell(8).getStringCellValue();
					Double spCost = getCellValueAsNumber(row.getCell(9));
					System.out.println(row.getCell(8).getStringCellValue());
					if (!servicePackageName.isEmpty() && spCost != null) {
						SubscriptionPackage sp = spRepo.findByOperatorIdAndName(op.getId(), servicePackageName);
						// Inserting data into Subscription Package
						if (sp == null) {
							// System.out.println(sp.toString());
							sp = new SubscriptionPackage();
							sp.setCost(spCost);
							sp.setName(servicePackageName);
							sp.setOperator(op);
							spRepo.save(sp);
						}

						if (row.getCell(3).getStringCellValue() != null) {
							Customer cust = new Customer();
							cust.setCustomerName(row.getCell(3).getStringCellValue());
							cust.setAadharNumber(row.getCell(5).getStringCellValue());
							cust.setAddress(row.getCell(7).getStringCellValue());
							cust.setBoxId(row.getCell(0).getStringCellValue());
							cust.setCardNumber(row.getCell(1).getStringCellValue());
							cust.setCode(row.getCell(10).getStringCellValue());
							cust.setManufacturer(row.getCell(2).getStringCellValue());
							cust.setOperator(op);
							cust.setPackageId(sp.getId());
							cust.setPhoneNumber(row.getCell(4).getStringCellValue());
							cust.setStatus("Active");
							cust.setZone(row.getCell(6).getStringCellValue());
							custRepo.save(cust);
						}
					}
				}
				count = count + 1;
				System.out.println(count);

				/*
				 * for (Cell cell : row) {
				 * 
				 * 
				 * 
				 * //String cellValue = dataFormatter.formatCellValue(cell);
				 * 
				 * byte[] utf8 = cell.getStringCellValue().getBytes("UTF-8");
				 * 
				 * // Convert from UTF-8 to Unicode String cellValue= new String(utf8, "UTF-8");
				 * System.out.print(cellValue); }
				 */
				// System.out.println();
			}
			System.out.println("Total number of Rows in excell >>>>>" + count);

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ServiceResponse(
				new ResponseStatus(200, "Operator Data Setup Successfull. Updated customer count>>" + count + ""));

	}

	public File convert(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			return convFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Double getCellValueAsNumber(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			if (!cell.getRichStringCellValue().getString().isEmpty()) {
				return new Double(cell.getRichStringCellValue().getString());
			} else {
				return null;
			}
		case NUMERIC:
			return cell.getNumericCellValue();
		case BLANK:
			return null;
		default:
			return null;
		}
	}
}
