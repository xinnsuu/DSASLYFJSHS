package com.xinnsuu.seatflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnsuu.seatflow.model.AcademicStructure;
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;

@Service
public class AcademicStructureServiceImpl implements AcademicStructureService {

    @Autowired
    private AcademicStructureRepository academicStructureRepository;

    @Override
    public List<AcademicStructure> getAllSections() {
        return academicStructureRepository.findAll();
    }

    @Override
    public Optional<AcademicStructure> getSectionById(Long id) {
        return academicStructureRepository.findById(id);
    }

    @Override
    public AcademicStructure createAcademicStructure(AcademicStructure academicStructure) {
        return academicStructureRepository.save(academicStructure);
    }

    @Override
    public AcademicStructure updateAcademicStructure(Long id, AcademicStructure updatedStructure) {
        Optional<AcademicStructure> existingStructureOpt = academicStructureRepository.findById(id);

        if (existingStructureOpt.isPresent()) {
            AcademicStructure existingStructure = existingStructureOpt.get();
            
            existingStructure.setGradeLevel(updatedStructure.getGradeLevel());
            existingStructure.setStrand(updatedStructure.getStrand());
            existingStructure.setSectionName(updatedStructure.getSectionName());
            
            return academicStructureRepository.save(existingStructure);
        } else {
            throw new RuntimeException("Academic Structure with ID " + id + " not found");
        }
    }

    @Override
    public void deleteAcademicStructure(Long id) {
        if (!academicStructureRepository.existsById(id)) {
            throw new RuntimeException("Academic Structure with ID " + id + " not found");
        }
        academicStructureRepository.deleteById(id);
    }
}