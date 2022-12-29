package com.museum.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Audio_file;
import com.museum.service.Audio_fileService;
import com.museum.service.BeaconService;

@Controller
public class Audio_fileController {
 
	@Autowired
	private Audio_fileService audioService; 
	
	@Autowired
	private BeaconService beaconService;
	

	
	@GetMapping("/viewAudio")
	public String viewAudio(Model model) {
		model.addAttribute("listAudio", audioService.getAllAudio_file());
		
		return "audio_list";
	}
	
	@GetMapping("/addAudio")
	public String addAudio(Audio_file audio, Model model) {
		model.addAttribute("listBeacon", beaconService.getAllBeacon());
		model.addAttribute("audio", audio);
		return "add_audio";
	}
	
	public String uploadDirectory=System.getProperty("User.dir")+"/src/main/resources/static/images";
	
	@RequestMapping(value="/saveAudio", consumes = {"multipart/form-data" })
	public String saveAudio(@ModelAttribute("audio") Audio_file audio, @RequestPart("file") MultipartFile file, Model model) {
		

		model.addAttribute("listBeacon", beaconService.getAllBeacon());
		try {
			audio.setAudio_file(file.getBytes());
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		file.getOriginalFilename();

		String filename = audio.getId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);

		// System.out.println(filename);
		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}

		audio.getAudio_file();
		audioService.saveAudio_file(audio);
		
		return "redirect:/viewAudio";
	}
	
	@RequestMapping("/updateAudio/{id}")
	public String updateAudio(@PathVariable(value = "id")long id,Model model) {
		Audio_file audio_file = audioService.getAudio_fileById(id);
		model.addAttribute("listBeacon", beaconService.getAllBeacon());
		model.addAttribute(audio_file);
		
		return "update_audio";
	}
	
	@RequestMapping(value = "/deleteAudio/{id}", method = RequestMethod.GET)
	public String deleteThroughId(@PathVariable(value = "id") long id) {

		audioService.deleteAudio_fileById(id);
		return "redirect:/viewAudio";
	}
	
	@RequestMapping(value = "/Status/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean SaveStatus(@PathVariable(value = "id") long id) {
	   System.out.println("inside the method");
		audioService.ChangeStatus(id);
		return true;
	}
	
	@GetMapping("/showAudio/{id}")
    public void showProductImage(@PathVariable Long id, HttpServletResponse resp)throws IOException {
        resp.setContentType("audio/mp3");
        try {
            Audio_file audio_file = audioService.findById(id);
            InputStream is = new ByteArrayInputStream(audio_file.getAudio_file());
            IOUtils.copy(is, resp.getOutputStream());
        } catch (Exception e) {
            throw new ExportException("audio is not available");
        }
    }
	
	
	
}
