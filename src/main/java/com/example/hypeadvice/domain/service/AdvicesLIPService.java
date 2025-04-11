package com.example.hypeadvice.domain.service;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.utils.Utils;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.example.hypeadvice.domain.vo.AdviceVO;
import com.example.hypeadvice.domain.vo.Slip;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AdvicesLIPService {

    public Advice gerar() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://api.adviceslip.com/advice")
                .header("Accept-Language", "br")
                .header("Content-Type", "application/json")
                .asString();
        int status = response.getStatus();
        if (HttpStatus.SC_OK == status) {
            AdviceVO adviceVO = null;
            try {
                String body = response.getBody();
                adviceVO = Utils.jsonToObject(AdviceVO.class, body);
            } catch (Exception e) {
                throw new RuntimeException("Status Code" + status + ", message " + e.getMessage());
            }

            if (adviceVO != null) {
                Slip slip = adviceVO.getSlip();
                String adviceStr = slip.getAdvice();
                return new Advice(adviceStr);
            } else {
                throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
            }
        }
        else {
            throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
        }
    }

    public AdviceListVO buscarByDescricao(String descricao) throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://api.adviceslip.com/advice/search/" + descricao)
                .header("Accept-Language", "br")
                .header("Content-Type", "application/json")
                .asString();
        int status = response.getStatus();
        if (HttpStatus.SC_OK == status) {
            AdviceListVO vo = null;
            try {
                String body = response.getBody();
                if (body.contains("No advice slips found matching that search term")) {
                    throw new RuntimeException("Status Code" + status + ", message: No advice slips found matching that search term");
                }
                vo = Utils.jsonToObject(AdviceListVO.class, body);
            } catch (Exception e) {
                throw new RuntimeException("Status Code" + status + ", message " + e.getMessage());
            }

            if (vo != null) {
                return vo;
            } else {
                throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
            }
        }
        else {
            throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
        }
    }
}
