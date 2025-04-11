package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.example.hypeadvice.domain.vo.Slip;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class AdviceBean extends Bean implements Serializable {

    @Autowired
    private AdviceService adviceService;

    private Advice advice = new Advice();
    private List<Advice> advices;
    private AdviceListVO adviceListVO = new AdviceListVO();
    private Integer id;

    @PostConstruct
    public void initBean() {
        advices = adviceService.findAll();
    }

    public void salvar() {
        if (advice.getTipo() == null) {
            addFaceMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione o Tipo de Conselho (Gratuito ou Pago).");
            return;
        }

        adviceService.save(advice);
        advices.add(advice);
        adicionarAdvice();

        addFaceMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Conselho cadastrado com sucesso!");
    }

    public void gerar() {
        try {
            advice = adviceService.gerar();
        } catch (UnirestException e) {
            addMessageError(e);
        }
    }

    public void adicionarAdvice() {
        advice = new Advice();
    }

    // Nova funcionalidade: busca de conselho por ID via API
    public void buscarPorId() {
        try {
            Advice conselho = adviceService.buscarPorId(this.id);

            if (conselho != null) {
                Slip slip = new Slip();
                slip.setId(conselho.getId());
                slip.setAdvice(conselho.getDescricao());
                slip.setDate(new Date());

                List<Slip> slips = new ArrayList<>();
                slips.add(slip);

                adviceListVO.setSlips(slips);
                adviceListVO.setTotal_results(1);
                adviceListVO.setQuery(String.valueOf(id));

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Conselho encontrado com sucesso!", null));
            } else {
                adviceListVO.setSlips(Collections.emptyList());
                adviceListVO.setTotal_results(0);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum conselho encontrado para o ID informado.", null));
            }

        } catch (UnirestException e) {
            adviceListVO.setSlips(Collections.emptyList());
            adviceListVO.setTotal_results(0);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar conselho", e.getMessage()));
        }
    }

    // Getters e Setters
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public List<Advice> getAdvices() {
        return advices;
    }

    public void setAdvices(List<Advice> advices) {
        this.advices = advices;
    }

    public AdviceListVO getAdviceListVO() {
        return adviceListVO;
    }

    public void setAdviceListVO(AdviceListVO adviceListVO) {
        this.adviceListVO = adviceListVO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
