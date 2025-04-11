package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class AdviceBean extends Bean {

    @Autowired AdviceService adviceService;

    private Advice advice = new Advice();
    private List<Advice> advices;

    public void initBean() {
        advices = adviceService.findAll();
    }

    public List<Advice> getAdvices() {
        return advices;
    }

    public void setAdvices(List<Advice> advices) {
        this.advices = advices;
    }

    public void salvar() {
        adviceService.save(advice);
        advices.add(advice);
        adicionarAdvice();
        addFaceMessage(FacesMessage.SEVERITY_INFO, "Sucesso", null);
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

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
