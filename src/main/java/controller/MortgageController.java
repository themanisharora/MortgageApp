package controller;

import com.hackathon.MortgageDemo.model.BaseResponse;
import com.hackathon.MortgageDemo.model.TransactionRequest;
import com.hackathon.MortgageDemo.service.MortgageService;
import com.hackathon.MortgageDemo.utils.ResponseBuilder;
import constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(AppConstants.API_BASE_PATH)
@RequiredArgsConstructor
public class MortgageController {
    private final MortgageService mortgageService;

    @PostMapping("/postTransaction")
    public ResponseEntity<BaseResponse<Boolean>> postTransaction(@RequestBody TransactionRequest transaction) {

          return ResponseBuilder.success(HttpStatus.OK,AppConstants.TRANSACTION_SUCCESS,mortgageService.postTransaction(transaction));
    }

}
