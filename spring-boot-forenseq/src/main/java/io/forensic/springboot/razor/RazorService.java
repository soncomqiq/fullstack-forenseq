package io.forensic.springboot.razor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazorService {
	
	@Autowired
	private RazorRepository razorRepository;
	
	public List<Razor> getAllForenseqs(){
		//return topics;
		List<Razor> razors = new ArrayList<>();
		razorRepository.findAll()
		.forEach(razors::add);
		return razors;
	}
	
	public Optional<Razor> getForenseq(RazorIdentity id) {
		return razorRepository.findById(id);
	}

	public void addForenseq(Razor razor) {
		razorRepository.save(razor);
	}

	public void updateForenseq(String id, Razor razor) {
		razorRepository.save(razor);
	}

	public void deleteForenseq(RazorIdentity id) {
		razorRepository.deleteById(id);
	}
}
