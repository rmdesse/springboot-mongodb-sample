package org.home.devhub.faq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.home.devhub.faq.exception.ResourceNotFoundException;
import org.home.devhub.faq.model.Faq;
import org.home.devhub.faq.repository.FaqRepository;
import org.home.devhub.faq.service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class FaqController {
	
	private static final  Logger logger = LoggerFactory.getLogger(FaqController.class);

	
	@Autowired
	private FaqRepository faqRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/faqs")
	public List<Faq> getAllfaqs() {
        logger.debug("getAllfaqs");
		return faqRepository.findAllByOrderByPositionAsc();
	}

	@GetMapping("/faqs/{id}")
	public ResponseEntity<Faq> getfaqById(@PathVariable(value = "id") Long faqId)
			throws ResourceNotFoundException {
        logger.debug("getfaqById {}", faqId);
		Faq faq = faqRepository.findById(faqId)
				.orElseThrow(() -> new ResourceNotFoundException("Faq not found for this id :: " + faqId));
		return ResponseEntity.ok().body(faq);
	}

	@PostMapping("/faqs")
	public Faq createfaq(@Valid @RequestBody Faq faq) {
        logger.debug("createfaq");
		faq.setId(sequenceGeneratorService.generateSequence(Faq.SEQUENCE_NAME));
		return faqRepository.save(faq);
	}

	@PutMapping("/faqs/{id}")
	public ResponseEntity<Faq> updatefaq(@PathVariable(value = "id") Long faqId,
			@Valid @RequestBody Faq faqDetails) throws ResourceNotFoundException {
        logger.debug("updatefaq {}", faqId);
		Faq faq = faqRepository.findById(faqId)
				.orElseThrow(() -> new ResourceNotFoundException("Faq not found for this id :: " + faqId));

		faq.setQuestion(faqDetails.getQuestion());
		faq.setAnswer(faqDetails.getAnswer());
		faq.setPosition(faqDetails.getPosition());
		final Faq updatedfaq = faqRepository.save(faq);
		return ResponseEntity.ok(updatedfaq);
	}

	@DeleteMapping("/faqs/{id}")
	public Map<String, Boolean> deletefaq(@PathVariable(value = "id") Long faqId)
			throws ResourceNotFoundException {
        logger.debug("deletefaq {}", faqId);
		Faq faq = faqRepository.findById(faqId)
				.orElseThrow(() -> new ResourceNotFoundException("Faq not found for this id :: " + faqId));

		faqRepository.delete(faq);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
