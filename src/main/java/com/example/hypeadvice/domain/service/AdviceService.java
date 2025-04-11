package com.example.hypeadvice.domain.service;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.repository.AdviceRepository;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdviceService {

    @Autowired
    public AdviceRepository adviceRepository;

    @Autowired
    public AdvicesLIPService advicesLIPService;

    @Transactional(rollbackFor = Exception.class)
    public Advice save(Advice analiseContrato) {
        return adviceRepository.saveAndFlush(analiseContrato);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Advice> findAll() {
        return adviceRepository.findAll();
    }

    public Advice gerar() throws UnirestException {
        return advicesLIPService.gerar();
    }

    public AdviceListVO buscar(Advice advice) throws UnirestException {
        String descricao = advice.getDescricao();
        if (StringUtils.isNotBlank(descricao)) {
            return advicesLIPService.buscarByDescricao(descricao);
        }
        return null;
    }

    //  busca conselho por ID via API externa
    public Advice buscarPorId(int id) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://api.adviceslip.com/advice/" + id)
                .header("accept", "application/json")
                .asJson();

        JSONObject responseBody = response.getBody().getObject();

        if (responseBody.has("message")) {
            JSONObject message = responseBody.getJSONObject("message");
            String errorText = message.optString("text", "Erro desconhecido ao buscar conselho.");
            throw new UnirestException("Conselho não encontrado para o ID: " + id + " - " + errorText);
        }

        JSONObject slip = responseBody.optJSONObject("slip");

        if (slip == null) {
            throw new UnirestException("Conselho não encontrado para o ID: " + id);
        }

        Advice advice = new Advice();
        advice.setId((long) slip.getInt("id"));
        advice.setDescricao(slip.getString("advice"));
        return advice;
    }
}
