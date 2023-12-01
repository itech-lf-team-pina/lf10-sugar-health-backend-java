package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.dto.response.PageDTO;
import com.health.sugar.lf10sugarhealth.model.Product;
import com.health.sugar.lf10sugarhealth.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/")
    public ResponseEntity<PageDTO> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "25", required = false) int pageSize
    ) {
        try {

            // create Pageable instance
            Pageable pageable = PageRequest.of(
                    pageNo,
                    pageSize,
                    Sort.by("categoryGerman").ascending()
            );

            Page<Product> page = productRepository.findAll(pageable);

            PageDTO pageDTO = new PageDTO(
                    page.stream().toList(),
                    page.isFirst(),
                    page.isLast(),
                    page.getTotalPages(),
                    page.getSize(),
                    page.getNumber(),
                    page.getTotalElements(),
                    page.getNumberOfElements()
            );


            if (page.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(pageDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);

            return productOptional
                    .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO> searchProductByEveryTextEntry(
            @RequestParam(value = "search", defaultValue = "0", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "25", required = false) int pageSize
    ) {
        try {
            // create Pageable instance
            Pageable pageable = PageRequest.of(
                    pageNo,
                    pageSize,
                    Sort.by("categoryGerman").ascending()
            );

            Page<Product> productPage = productRepository
                    .searchByCategoryOrCategoryGermanOrDescriptionOrDescriptionGerman(
                            search,
                            search,
                            search,
                            search,
                            pageable);


            PageDTO pageDTO = new PageDTO(
                    productPage.stream().toList(),
                    productPage.isFirst(),
                    productPage.isLast(),
                    productPage.getTotalPages(),
                    productPage.getSize(),
                    productPage.getPageable().getPageNumber(),
                    productPage.getTotalElements(),
                    productPage.getNumberOfElements()
            );

            if (productPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(pageDTO, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
