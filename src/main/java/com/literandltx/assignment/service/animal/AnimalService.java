package com.literandltx.assignment.service.animal;

import com.literandltx.assignment.dto.AnimalResponse;
import com.literandltx.assignment.dto.AnimalSearchSortRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public interface AnimalService {
    ResponseEntity<Void> upload(
            final MultipartFile file
    );

    ResponseEntity<List<AnimalResponse>> search(
            final AnimalSearchSortRequest request,
            final Pageable pageable
    );
}
