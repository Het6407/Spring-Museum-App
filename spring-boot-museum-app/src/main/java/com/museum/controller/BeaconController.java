package com.museum.controller;

import java.security.InvalidParameterException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.museum.Utility.CommonUtility;
import com.museum.model.Beacon;
import com.museum.service.BeaconService;
import com.museum.service.MuseumService;

@Controller
public class BeaconController {

	@Autowired
	private BeaconService beaconService;
	
	@Autowired
	private MuseumService museumService;
	
	@GetMapping("/beacon")
	public String viewBeacon(Model model) {
		model.addAttribute("listBeacon", beaconService.getAllBeacon());
		return "beacon";
	}
	
	@GetMapping("/addBeacon")
	public String addBeacon(Beacon beacon,Model model) {
		model.addAttribute("beacon", beacon);
	
		return "add_Beacon";
	}
	/*
	 * @PostMapping("/saveBeacon") public String
	 * saveBeacon(@ModelAttribute("beacon") Beacon beacon,Model model) {
	 * 
	 * beaconService.saveBeacon(beacon); model.addAttribute("Beacon",beacon); return
	 * "redirect:/beacon"; }
	 */
	
	@RequestMapping(value = "/deleteBeacon/{id}", method = RequestMethod.GET)
	public String deleteBeacon(@PathVariable(value = "id")long id) {
		beaconService.deleteBeaconById(id);
		return "redirect:/beacon";
	}
	
	@GetMapping("/updateBeacon/{id}")
	public String updateBeacon(@PathVariable(value = "id") long id, Model model) {
		Beacon beacon = beaconService.getBeaconById(id);
		
		model.addAttribute("beacon", beacon);
		return "update_Beacon";
	}
	
	
	
	 @PostMapping("/saveBeacon")
	    public String saveMuseum(@Valid @ModelAttribute("beacon") Beacon beacon, BindingResult result, Model model, @RequestParam(value="beacon-image", required=false) MultipartFile[] images) {
	        try {
	        	System.out.println(images);
	            beaconService.saveBeacon(beacon, CommonUtility.getAuthenticatedUser().getId(), images);
	            
	        } catch (InvalidParameterException e) {
	        	
	            model.addAttribute("beaconError", e.getMessage());
	            
	            return "add_Beacon";
	            
	        } catch (ConstraintViolationException e) {
	        	
	            model.addAttribute("beaconError", e.getMessage());
	            
	            return "add_Beacon";
	            
				/*
				 * } catch (Exception e) {
				 * 
				 * model.addAttribute("beaconError", e.getMessage());
				 * 
				 * return "add_Beacon";
				 */
	        }
	       model.addAttribute("beacon",beacon);
	        return "redirect:/beacon";
	    }
	
	
		/*
		 * @GetMapping("/showImage/{id}") public void showProductimage(@PathVariable
		 * Long id, HttpServletResponse resp)throws IOException {
		 * resp.setContentType("image/jpeg"); try { Beacon beacon =
		 * beaconService.findById(id); InputStream is = new
		 * ByteArrayInputStream(beacon.getImage()); IOUtils.copy(is,
		 * resp.getOutputStream()); } catch (Exception e) { throw new
		 * ExportException("Image is not available"); } }
		 */
	
}
