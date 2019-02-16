package com.deeps.easycable.api.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.repo.BulkOperationRepo;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.SubscriptionType;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerService;
import com.deeps.easycable.api.service.OperatorDataSetupService;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OperatorDataSetupServiceImpl implements OperatorDataSetupService {

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	@Autowired
	CustomerRepo custRepo;
	
	@Autowired
	BulkOperationRepo bulkRepo;

	@Autowired
	CustomerService custService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public ServiceResponse setupOperatorData(MultipartFile file, Long operatorId) {

		int uploadedRowcount = 0;
		int customerCount = 0;
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			log.info("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			Sheet sheet = workbook.getSheetAt(0);
			log.info(sheet.getHeader());
			// DataFormatter dataFormatter = new DataFormatter();
			log.info("\n\nIterating over Rows and Columns using for-each loop\n");

			// Create a new Operator to be Deleted later
			Operator op = new Operator();
			op.setName(file.getOriginalFilename().replaceAll(".xls", ""));
			op.setOperatorAgencyName(op.getName());
			op.setMaxUser(1000);
			op.setSubscriptionCost(new Double(2999));
			op.setSubscriptionStatus("Active");
			op.setPassword(passwordEncoder.encode("password"));
			op.setSubscriptionType(SubscriptionType.BASIC);
			opRepo.save(op);

			int totalCustomerCount = custRepo.findByOperatorId(operatorId).size();
			int subscriptionMaxCount = opRepo.findById(operatorId).get().getMaxUser();

			ArrayList<Customer> customerList = new ArrayList<Customer>();
			List<SubscriptionPackage> subscriptionPackageList = spRepo.findByOperatorId(operatorId);
			for (Row row : sheet) {
				// Skipping Header with uploadedRowCount >1 option
				if (uploadedRowcount > 0) {
					String servicePackageName = row.getCell(8).getStringCellValue();
					Double spCost = getCellValueAsNumber(row.getCell(9));
					log.info(row.getCell(8).getStringCellValue());
					if (!servicePackageName.isEmpty() && spCost != null) {
						SubscriptionPackage sp = subscriptionPackageList.stream()
								.filter(subscription -> subscription.getName().equalsIgnoreCase(servicePackageName))
								.findFirst().orElse(null);
						// Inserting data into Subscription Package
						if (sp == null) {
							log.info("Create a newPackage >>" + servicePackageName);
							sp = new SubscriptionPackage();
							sp.setCost(spCost);
							sp.setName(servicePackageName);
							sp.setOperator(op);
							spRepo.save(sp);
							subscriptionPackageList.add(sp);
						}

						if (row.getCell(3).getStringCellValue() != null) {
							if (subscriptionMaxCount > totalCustomerCount) {
								Customer cust = new Customer();
								cust.setCustomerName(row.getCell(3).getStringCellValue());
								cust.setAadharNumber(row.getCell(5).getStringCellValue());
								cust.setAddress(row.getCell(7).getStringCellValue());
								cust.setBoxId(row.getCell(0).getStringCellValue());
								cust.setCardNumber(row.getCell(1).getStringCellValue());
								cust.setCode(row.getCell(10).getStringCellValue());
								cust.setManufacturer(row.getCell(2).getStringCellValue());
								cust.setOperator(op);
								cust.setPackages(Arrays.asList(sp));
								cust.setPhoneNumber(row.getCell(4).getStringCellValue());
								cust.setStatus("Active");
								cust.setQrCode(UUID.randomUUID().toString());
								cust.setZone(row.getCell(6).getStringCellValue());
								cust.setSubscriptionCost(spCost);
								customerList.add(cust);
								customerCount++;
								totalCustomerCount++;
								log.info(cust.toString());
							}
						}
					}
				}
				uploadedRowcount = uploadedRowcount + 1;
				log.info(uploadedRowcount);
			}
			bulkRepo.saveAllCustomer(customerList);
			//custRepo.saveAll(customerArray);
			log.info("Total number of Rows in excel >>>>>" + uploadedRowcount + ">> Processed Count>" + customerCount);

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ServiceResponse(new ResponseStatus(200,
				"Operator Data Setup Successfull. Updated customer count " + customerCount + ""));

	}

	public File convert(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			@Cleanup
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			return convFile;
		} catch (IOException e) {
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
