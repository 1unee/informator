package com.oneune.informator.rest.controllers;

import com.oneune.informator.rest.services.RussianMailIntegrationService;
import com.oneune.informator.rest.store.dtos.russian_mail.OperationHistoryDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("russian-mail")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RussianMailIntegrationController {

    RussianMailIntegrationService russianMailIntegrationService;

    @GetMapping("parcels/{barcode}/operation-history")
    public OperationHistoryDto trackPackage(@PathVariable String barcode) {
        return russianMailIntegrationService.integrate(barcode);
    }
}
