package com.ojas.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.timesheet.entity.Technology;
import com.ojas.timesheet.entity.UserRegistarion;
import com.ojas.timesheet.repo.TechnologyRepo;
import com.ojas.timesheet.repo.UserRegistarionRepo;
import com.ojas.timesheet.service.impl.UserRegistarionService;

@Controller
public class UserRegistarionController {

	@Autowired
	private UserRegistarionService userRegistarionService;
	@Autowired
	private UserRegistarionRepo userRegistarionRepo;
	@Autowired
	private TechnologyRepo techRepo;

	@RequestMapping(value = "user/registration", method = RequestMethod.GET)
	public String home(Model model, UserRegistarion UserRegistarion) {
		List<Technology> techDetails = techRepo.findAll();
		model.addAttribute("UserRegistarion", UserRegistarion);
		model.addAttribute("techDetails", techDetails);
		return "user/UserRegistarionPage";
	}

	
	@PostMapping("user/registration")
	public String save(@RequestParam("name") String name, @RequestParam("employeeId") int employeeId,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phoneNumber") double phoneNumber, @RequestParam("domain") String domain,
			@RequestParam("gender") String gender, @RequestParam("userAvtarPic") MultipartFile userAvtarPic,
			ModelMap model) {
		
		
		
		
		if (isAlpha(name)) {
            if (doConvert(name)) {
               // doConvert(name);
            }
        } else {
        	model.addAttribute("error", "User name allowed only characters " + name);
			return "user/UserRegistarionPage";
//            System.out.println("User name allowed only characters");
        }
		
		
		List<UserRegistarion> findall = userRegistarionRepo.findAll();
		for (UserRegistarion find : findall) {

			if (find.getEmployeeId() == employeeId) {
				model.addAttribute("error", "user id already exists...! " + employeeId);
				return "user/UserRegistarionPage";
			}

			if (find.getEmail().equalsIgnoreCase(email) || find.getEmail().contentEquals(email)) {
				model.addAttribute("error", "email already exists...! " + email);
				return "user/UserRegistarionPage";
			}
			if (find.getPhoneNumber() == phoneNumber) {
				model.addAttribute("error", "phone number already exists...! " + phoneNumber);
				return "user/UserRegistarionPage";

			}
			String fileName = userAvtarPic.getOriginalFilename().toString();
			int index = fileName.lastIndexOf('.');
			String extension;
			if (index > 0) {
				extension = fileName.substring(index + 1);
//				System.out.println("File extension is " + extension);
				if (extension.equalsIgnoreCase("jpg")) {
				} else {
					model.addAttribute("error", "please upload jpg... ");
					return "user/UserRegistarionPage";
				}

			}

		}

		try

		{
			userRegistarionService.inserRecords(name, employeeId, email, password, phoneNumber, domain, gender,
					userAvtarPic);
		} catch (Exception e) {
			model.addAttribute("error", "failed...!");
			return "user/UserRegistarionPage";
		}

		model.addAttribute("message", "successfully registered...!");
		return "user/UserRegistarionPage";
	}

	
	public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean doConvert(String str) {
        char[] charArray = str.toCharArray();
        boolean foundSpace = true;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        str = String.valueOf(charArray);
        System.out.println("Name: " + str);
        return foundSpace;
    }
}