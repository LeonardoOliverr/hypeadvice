package com.example.hypeadvice.domain.service;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.repository.AdviceRepository;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdviceService {

    @Autowired public AdviceRepository adviceRepository;
    @Autowired public AdvicesLIPService advicesLIPService;

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
}
